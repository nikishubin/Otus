package ru.otus.patterns.multiplication.service;

public interface MathematicsOperationAvailability<T> {

    void checkAvailability(T first, T second);
}
