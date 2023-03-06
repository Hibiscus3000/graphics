package ru.nsu.fit.icg.lab1.instrument.parameter;

public class Parameter {

    private final String name;
    private final int min;
    private final int max;
    private final int minorTicks;
    private final int majorTicks;

    private final Value value;

    public Parameter(String name,
                     int min,
                     int max,
                     int value,
                     boolean requiresValue,
                     boolean valueInUse,
                     int minorTicks,
                     int majorTicks) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.minorTicks = minorTicks;
        this.majorTicks = majorTicks;
        if (requiresValue) {
            this.value = new Value(value, minorTicks, min, max);
        } else {
            this.value = new NotNecessaryValue(value, minorTicks, min, max, valueInUse);
        }
    }

    public Value getValue() {
        return value;
    }

    public boolean setValue(int value) {
        return this.value.setValue(value);
    }

    public boolean setValue(String value) {
        return this.value.setValue(value);
    }

    public String getName() {
        return name;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getMinorTicks() {
        return minorTicks;
    }

    public int getMajorTicks() {
        return majorTicks;
    }
}
