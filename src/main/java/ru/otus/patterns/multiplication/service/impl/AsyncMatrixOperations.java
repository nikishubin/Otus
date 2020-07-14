package ru.otus.patterns.multiplication.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.patterns.multiplication.data.Matrix;
import ru.otus.patterns.multiplication.service.MathematicsOperationAvailability;
import ru.otus.patterns.multiplication.service.MathematicsOperations;
import ru.otus.patterns.multiplication.service.factory.NamedWorkerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class AsyncMatrixOperations implements MathematicsOperations<Matrix>, MathematicsOperationAvailability<Matrix> {
    private final Logger log = LogManager.getLogger(AsyncMatrixOperations.class);

    private final NamedWorkerFactory threadFactory;

    public AsyncMatrixOperations() {
        this.threadFactory = new NamedWorkerFactory("Matrix");
    }

    @Override
    public Matrix multiply(Matrix first, Matrix second) {
        checkAvailability(first, second);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);

        var promisedMatrix = IntStream.range(0, first.getRows().size()).boxed()
                .map(i -> IntStream.range(0, second.getRows().get(0).size()).mapToObj(next -> CompletableFuture.completedFuture(0)).collect(Collectors.toList()))
                .collect(Collectors.toList());
        for (int row = 0; row < first.getRows().size(); row++) {
            for (int col = 0; col < second.getRows().get(0).size(); col++) {
                int finalRow = row;
                int finalCol = col;
                promisedMatrix.get(row).set(col, CompletableFuture.supplyAsync(() -> multiplyMatrixCell(first.getRows(), second.getRows(), finalRow, finalCol), executor));
            }
        }

        CompletableFuture.allOf(promisedMatrix.stream().flatMap(Collection::stream).toArray(CompletableFuture[]::new))
                .thenRun(executor::shutdown);

        Matrix.Builder matrix = Matrix.newInstance();
        promisedMatrix.forEach(promisedRow -> matrix.addRow(promisedRow.stream().map(CompletableFuture::join).toArray(Integer[]::new)));
        return matrix.create();
    }

    @Override
    public void checkAvailability(Matrix first, Matrix second) {
        if (first == null || second == null || first.getRows().isEmpty() || second.getRows().isEmpty()) {
            throw new UnsupportedOperationException("One of input matrix is empty!");
        }

        if (first.getRows().get(0).size() != second.getRows().size()) {
            throw new UnsupportedOperationException(String.format("First matrix columns count (%d) must be equal to second matrix rows count (%d)!", first.getRows().get(0).size(), second.getRows().size()));
        }
    }

    private int multiplyMatrixCell(List<List<Integer>> first, List<List<Integer>> second, int row, int col) {
        log.debug("Multiply row[{}] by column[{}]", row, col);
        int value = 0;
        for (int i = 0; i < second.size(); i++) {
            value += first.get(row).get(i) * second.get(i).get(col);
        }
        return value;
    }
}
