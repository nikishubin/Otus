package ru.otus.patterns.multiplication.logger.exception;

public class LoggerException extends RuntimeException {

    public LoggerException(String message, Throwable cause) {
        super(message, cause);
    }
}
