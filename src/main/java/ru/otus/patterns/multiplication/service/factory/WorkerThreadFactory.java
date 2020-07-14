package ru.otus.patterns.multiplication.service.factory;

import java.util.concurrent.ThreadFactory;

public class WorkerThreadFactory implements ThreadFactory {
    private final String name;
    private int counter;

    public WorkerThreadFactory(String workerName) {
        this.counter = 0;
        this.name = workerName;
    }

    @Override
    public Thread newThread(Runnable run) {
        return new Thread(run, String.format("%s worker No %d", name, ++counter));
    }
}
