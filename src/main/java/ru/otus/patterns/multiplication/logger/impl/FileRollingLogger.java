package ru.otus.patterns.multiplication.logger.impl;

import ru.otus.patterns.multiplication.logger.Logger;
import ru.otus.patterns.multiplication.logger.exception.LoggerException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileRollingLogger extends LoggerCore implements Logger {
    private final File logFile;

    private FileRollingLogger() {
        this.logFile = createLogFile();
    }

    public static FileRollingLogger getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public void write(String message) {
        try (Writer writer = Files.newBufferedWriter(logFile.toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            writer.write(enrichMessage(message));
        } catch (IOException ioException) {
            throw new LoggerException(ioException.getMessage(), ioException);
        }
    }

    private File createLogFile() {
        File targetFolder = new File(System.getProperty("user.dir") + "/logs");
        if (!targetFolder.exists() && !targetFolder.mkdirs()) {
            throw new IllegalStateException("Couldn't create directory: " + targetFolder);
        }

        String currentTime = LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        File createdFile = new File(targetFolder.getName(), currentTime + ".log");
        try {
            if (createdFile.createNewFile()) {
                return createdFile;
            }
            return createdFile;
        } catch (IOException ioException) {
            throw new LoggerException(ioException.getMessage(), ioException);
        }
    }

    private static class SingletonHolder {
        public static final FileRollingLogger HOLDER_INSTANCE = new FileRollingLogger();
    }
}
