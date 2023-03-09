package ru.nsu.fit.icg.lab1.instrument.parameter;

import ru.nsu.fit.icg.lab1.instrument.parameter.listener.ValueListener;

import java.util.ArrayList;
import java.util.List;

public class Value implements Comparable {

    private final List<ValueListener> valueListenerList = new ArrayList<>();

    public enum ValueType {
        BOUNDED, MODULO
    }

    private ValueType valueType;

    private int value;
    private final int step;
    private final int min;
    private final int max;

    public Value(ValueType valueType, int value, int step, int min, int max) {
        this.valueType = valueType;
        this.value = value;
        this.step = step;
        this.min = min;
        this.max = max;
    }

    public boolean setValue(Integer value, ValueListener setter) {
        if (ValueType.BOUNDED == valueType) {
            if (value > max || value < min) {
                return false;
            }
            this.value = value;
            setAllListeners(setter);
        } else {
            this.value = value;
            setAllListeners(setter);
            if (value < min) {
                this.value = value + (max - min);
            }
            if (value > max) {
                this.value = value + (min - max);
            }
        }
        return true;
    }

    public void changeValue(int amount) {
        value = Math.max(Math.min(value + amount, max), min);
        setAllListeners(null);
    }

    public int getValue() {
        return value;
    }

    public int getStep() {
        return step;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void addValueListener(ValueListener valueListener) {
        valueListenerList.add(valueListener);
    }

    public void setAllListeners(ValueListener exceptional) {
        for (ValueListener valueListener : valueListenerList) {
            if (exceptional != valueListener) {
                valueListener.setValue(value);
            }
        }
    }

    public void clearListeners() {
        valueListenerList.clear();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Value) {
            Value other = (Value) o;
            return max - min - other.max + other.min;
        }
        return 1;
    }

}
