package ru.otus.patterns.multiplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.patterns.multiplication.data.Matrix;
import ru.otus.patterns.multiplication.service.impl.AsyncMatrixOperations;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class MatrixMultiplication {
    private static final Logger log = LogManager.getLogger(MatrixMultiplication.class);

    private static final Random RANDOM = new Random();
    private static final AsyncMatrixOperations mathematics = new AsyncMatrixOperations();

    public static void main(String[] args) {
        CompletableFuture<Matrix> firstPromise = CompletableFuture.completedFuture(randomizeMatrix(8, 8));
        CompletableFuture<Matrix> secondPromise = CompletableFuture.completedFuture(randomizeMatrix(8, 8));

        log.info("Matrix multiplication started!");
        Matrix multiplicationResult = mathematics.multiply(firstPromise.join(), secondPromise.join());
        log.info("Multiplication result:\n{}", multiplicationResult);
    }

    private static Matrix randomizeMatrix(int row, int column) {
        Matrix.Builder builder = Matrix.newInstance();
        IntStream.range(0, row).forEach(
                nextRow -> builder.addRow(IntStream.range(0, column).boxed().map(next -> RANDOM.nextInt(6)).toArray(Integer[]::new))
        );
        return builder.create();
    }
}
