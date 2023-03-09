package ru.nsu.fit.icg.lab1.instrument.parameter.listener;

public interface ValueErrorListener {

    void addError(String warningMsg, String parameterName);

    void removeError(String parameterName);
}
