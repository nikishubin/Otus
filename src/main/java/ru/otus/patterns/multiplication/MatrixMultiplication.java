package ru.otus.patterns.multiplication;

import ru.otus.patterns.multiplication.data.Matrix;
import ru.otus.patterns.multiplication.logger.Logger;
import ru.otus.patterns.multiplication.logger.impl.FileRollingLogger;
import ru.otus.patterns.multiplication.service.impl.AsyncMatrixOperations;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class MatrixMultiplication {
    private static final Logger log = FileRollingLogger.getInstance();
    private static final Random RANDOM = new Random();

    private static final AsyncMatrixOperations mathematics;

    static {
        mathematics = new AsyncMatrixOperations();
    }

    public static void main(String[] args) {
        CompletableFuture<Matrix> firstPromise = CompletableFuture.completedFuture(randomizeMatrix(8, 7));
        CompletableFuture<Matrix> secondPromise = CompletableFuture.completedFuture(randomizeMatrix(7, 8));

        log.write("Matrix multiplication started!");
        Matrix multiplicationResult = mathematics.multiply(firstPromise.join(), secondPromise.join());
        log.write(String.format("Multiplication result: %n %s", multiplicationResult));
    }

    private static Matrix randomizeMatrix(int row, int column) {
        Matrix.Builder builder = Matrix.newInstance();
        IntStream.range(0, row).forEach(
                nextRow -> builder.addRow(IntStream.range(0, column).boxed().map(next -> RANDOM.nextInt(5)).toArray(Integer[]::new))
        );
        return builder.create();
    }
}
