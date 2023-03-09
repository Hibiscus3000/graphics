package ru.nsu.fit.icg.lab1.instrument.parameter;

public class Parameter implements Comparable {

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
                     Value.ValueType valueType,
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
            this.value = new Value(valueType, value, minorTicks, min, max);
        } else {
            this.value = new NotNecessaryValue(value, minorTicks, min, max, valueInUse);
        }
    }

    public Value getValue() {
        return value;
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

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Parameter))
            return 1;
        return value.compareTo(((Parameter) o).getValue());
    }
}
