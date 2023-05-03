package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.container;

import java.util.HashMap;
import java.util.Map;

public abstract class Container<T> {

    private final Map<String, T> contained = new HashMap<>();

    public void putInContainer(String key, T value) {
        contained.put(key, value);
    }

    public T getContainedByKey(String key) {
        return contained.get(key);
    }

    public Map<String, T> getAllContained() {
        return contained;
    }

}
