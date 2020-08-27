package ru.otus.patterns.multiplication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.patterns.multiplication.data.Matrix;
import ru.otus.patterns.multiplication.service.impl.AsyncMatrixOperations;

import java.util.Collection;

class AsyncMatrixOperationsTest {

    private static AsyncMatrixOperations matrixOperations;

    @BeforeAll
    static void beforeAll() {
        matrixOperations = new AsyncMatrixOperations();
    }

    @ParameterizedTest
    @MethodSource("ru.otus.patterns.multiplication.args.MathematicsOperationsTestData#matrixMultiplicationData")
    void whenMatrixMultiplicationIsAvailableThenGetResult(Matrix first, Matrix second, Matrix expected) {
        Matrix actual = matrixOperations.multiply(first, second);

        Assertions.assertThat(actual.getRows().stream().flatMap(Collection::stream).toArray())
                .containsExactly(expected.getRows().stream().flatMap(Collection::stream).toArray());
    }

    @ParameterizedTest
    @MethodSource("ru.otus.patterns.multiplication.args.MathematicsOperationsTestData#matrixMultiplicationInvalidData")
    void whenMatrixMultiplicationIsUnavailableThenThrowUnsupported(Matrix first, Matrix second) {
        Assertions.assertThatThrownBy(() -> matrixOperations.multiply(first, second)).isInstanceOf(UnsupportedOperationException.class);
    }
}
