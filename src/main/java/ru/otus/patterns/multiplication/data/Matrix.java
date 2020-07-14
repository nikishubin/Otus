package ru.otus.patterns.multiplication.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Matrix {
    private static final CharSequence ROW_DELIMITER = "\n";
    private static final CharSequence COLUMN_DELIMITER = " ";

    @Getter
    private List<List<Integer>> rows;

    private Matrix() {
        this.rows = new ArrayList<>();
    }

    public static Builder newInstance() {
        return new Builder();
    }

    public static class Builder {
        private final Matrix matrix;
        private int maximumRowLength;

        public Builder() {
            this.matrix = new Matrix();
        }

        public Builder addRow(Integer... elements) {
            validateInput(elements);
            this.matrix.rows.add(List.of(elements));
            return this;
        }

        public Matrix create() {
            return this.matrix;
        }

        private void validateInput(Integer... elements) {
            if (maximumRowLength == 0) {
                maximumRowLength = elements.length;
            }

            if (elements.length == 0) {
                throw new IllegalArgumentException("Строка матрицы не может быть пуста!");
            }

            if (maximumRowLength != elements.length) {
                throw new IllegalArgumentException(String.format("Строка матрицы (%d) не может быть короче остальных (%d)!", elements.length, maximumRowLength));
            }
        }
    }

    @Override
    public String toString() {
        return getRows().parallelStream()
                .map(row -> row.stream()
                        .map(Objects::toString)
                        .collect(Collectors.joining(COLUMN_DELIMITER)))
                .collect(Collectors.joining(ROW_DELIMITER));
    }
}
