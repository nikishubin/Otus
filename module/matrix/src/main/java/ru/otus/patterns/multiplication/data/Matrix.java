package ru.otus.patterns.multiplication.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Matrix {
    private static final CharSequence ROW_DELIMITER = "\n";
    private static final CharSequence COLUMN_DELIMITER = " ";

    private List<List<Integer>> rows;

    private Matrix() {
        this.rows = new ArrayList<>();
    }

    public static Builder newInstance() {
        return new Builder();
    }

    public static final class Builder {
        private final Matrix matrix;
        private int maximumRowLength;

        private Builder() {
            this.matrix = new Matrix();
        }

        public Builder addRow(Integer... elements) {
            validateInput(elements);
            this.matrix.rows.add(List.of(elements));
            return this;
        }

        public Matrix create() {
            this.matrix.rows = Collections.unmodifiableList(this.matrix.rows);
            return this.matrix;
        }

        private void validateInput(Integer... elements) {
            if (maximumRowLength == 0) {
                maximumRowLength = elements.length;
            }

            if (elements.length == 0 || Arrays.stream(elements).anyMatch(Objects::isNull)) {
                throw new IllegalArgumentException("Matrix row can't be empty!");
            }

            if (maximumRowLength != elements.length) {
                throw new IllegalArgumentException(String.format("The entered matrix row (%d) can't be shorter than the rest (%d)!", elements.length, maximumRowLength));
            }
        }
    }

    public List<List<Integer>> getRows() {
        return rows;
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
