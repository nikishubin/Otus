package ru.otus.patterns.multiplication.args;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;
import ru.otus.patterns.multiplication.data.Matrix;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathematicsOperationsTestData {

    public static Stream<Arguments> matrixMultiplicationData() {
        return Stream.of(
                Arguments.arguments(
                        Matrix.newInstance()
                                .addRow(0, 0)
                                .addRow(0, 0)
                                .create(),
                        Matrix.newInstance()
                                .addRow(0, 0)
                                .addRow(0, 0)
                                .create(),
                        Matrix.newInstance()
                                .addRow(0, 0)
                                .addRow(0, 0)
                                .create()
                ),
                Arguments.arguments(
                        Matrix.newInstance()
                                .addRow(1, 1)
                                .addRow(2, 2)
                                .addRow(3, 3)
                                .create(),
                        Matrix.newInstance()
                                .addRow(1, 2, 3)
                                .addRow(1, 2, 3)
                                .create(),
                        Matrix.newInstance()
                                .addRow(2, 4, 6)
                                .addRow(4, 8, 12)
                                .addRow(6, 12, 18)
                                .create()
                ),
                Arguments.arguments(
                        Matrix.newInstance()
                                .addRow(2, 2)
                                .addRow(2, 2)
                                .create(),
                        Matrix.newInstance()
                                .addRow(1, 0)
                                .addRow(0, 1)
                                .create(),
                        Matrix.newInstance()
                                .addRow(2, 2)
                                .addRow(2, 2)
                                .create()
                ),
                Arguments.arguments(
                        Matrix.newInstance()
                                .addRow(-1, 2, -1)
                                .addRow(2, -1, 2)
                                .addRow(-1, 2, -1)
                                .create(),
                        Matrix.newInstance()
                                .addRow(1, -2, 1)
                                .addRow(-2, 1, -2)
                                .addRow(1, -2, 1)
                                .create(),
                        Matrix.newInstance()
                                .addRow(-6, 6, -6)
                                .addRow(6, -9, 6)
                                .addRow(-6, 6, -6)
                                .create()
                )
        );
    }

    public static Stream<Arguments> matrixMultiplicationInvalidData() {
        return Stream.of(
                Arguments.arguments(
                        Matrix.newInstance()
                                .addRow(-1, 2, -1)
                                .addRow(2, -1, 2)
                                .addRow(-1, 2, -1)
                                .create(),
                        Matrix.newInstance().create()
                ),
                Arguments.arguments(
                        Matrix.newInstance()
                                .addRow(-1, 2)
                                .addRow(2, -1)
                                .addRow(-1, 2)
                                .create(),
                        Matrix.newInstance()
                                .addRow(-1, 2)
                                .addRow(2, -1)
                                .addRow(-1, 2)
                                .create()
                )
        );
    }
}
