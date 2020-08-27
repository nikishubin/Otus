package ru.otus.patterns.multiplication.logger.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

abstract class LoggerCore {
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd hh:mm:ss.SSS";

    private static final String ROW_DELIMITER = "\n";
    private static final String ELEMENT_DELIMITER = "\t";

    protected String enrichMessage(String message) {
        String threadName = "[" + Thread.currentThread().getName() + "]";
        String moment = LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN));

        final StringJoiner rowJoiner = new StringJoiner(ELEMENT_DELIMITER);

        return rowJoiner.add(moment)
                .add(threadName)
                .add(message)
                .add(ROW_DELIMITER).toString();
    }
}
