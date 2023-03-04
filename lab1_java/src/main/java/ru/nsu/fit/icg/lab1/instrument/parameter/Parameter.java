package ru.nsu.fit.icg.lab1.instrument.parameter;

public class Parameter {

    private final String name;
    private final int lowerBorder;
    private final int upperBorder;
    private final int minorTicks;
    private final int majorTicks;
    private final boolean requiresValue;

    private int value;
    private boolean isSet = false;

    public Parameter(Parameter parameter) {
        this.name = parameter.name;
        this.lowerBorder = parameter.lowerBorder;
        this.upperBorder = parameter.upperBorder;
        this.value = parameter.value;
        this.minorTicks = parameter.minorTicks;
        this.majorTicks = parameter.majorTicks;
        this.requiresValue = parameter.requiresValue;
    }

    public Parameter(String name,
                     int lowerBorder,
                     int upperBorder,
                     int value,
                     int minorTicks,
                     int majorTicks,
                     boolean requiresValue) {
        this.name = name;
        this.lowerBorder = lowerBorder;
        this.upperBorder = upperBorder;
        this.value = value;
        this.minorTicks = minorTicks;
        this.majorTicks = majorTicks;
        this.requiresValue = requiresValue;
    }

    public String getName() {
        return name;
    }

    public int getLowerBorder() {
        return lowerBorder;
    }

    public int getUpperBorder() {
        return upperBorder;
    }

    public int getMinorTicks() {
        return minorTicks;
    }

    public int getMajorTicks() {
        return majorTicks;
    }

    public boolean requiresValue() {
        return requiresValue;
    }

    public Integer getValue() {
        return value;
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
        if (value > upperBorder || value < lowerBorder) {
            return false;
        }
        this.value = value;
        return true;
    }

    public void valueSet(boolean b) {
        isSet = b;
    }

    public boolean isSet() {
        return isSet;
    }
}
