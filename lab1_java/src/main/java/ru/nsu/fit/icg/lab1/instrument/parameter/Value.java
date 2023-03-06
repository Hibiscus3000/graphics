package ru.nsu.fit.icg.lab1.instrument.parameter;

public class Value {

    private int value;
    private final int step;
    private final int min;
    private final int max;

    public Value(int value, int step, int min, int max) {
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
        if (value > max || value < min) {
            return false;
        }
        this.value = value;
        return true;
    }

    public void changeValue(int amount) {
        value = Math.min(max, Math.max(min, value + amount));
    }

    public int getValue() {
        return value;
    }

    public int getStep() {
        return step;
    }


}
