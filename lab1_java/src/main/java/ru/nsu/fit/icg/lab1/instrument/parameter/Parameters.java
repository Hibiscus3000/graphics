package ru.nsu.fit.icg.lab1.instrument.parameter;

public interface Parameters {

    Integer getValue(String key);

    boolean isSet(String key);
}
