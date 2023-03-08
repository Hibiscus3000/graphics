package ru.nsu.fit.icg.lab1.instrument.parameter;

public class Value {

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

    public boolean setValue(String value) {
        try {
            int newValue = Integer.parseInt(value);
            return setValue(newValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean setValue(Integer value) {
        if (ValueType.BOUNDED == valueType) {
            if (value > max || value < min) {
                return false;
            }
            this.value = value;
        } else {
            this.value = value;
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
        setValue(value + amount);
    }

    public int getValue() {
        return value;
    }

    public int getStep() {
        return step;
    }


}
