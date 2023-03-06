package ru.nsu.fit.icg.lab1.instrument.parameter;

public class NotNecessaryValue extends Value {

    private boolean useValue;

    public NotNecessaryValue(int value, int step, int lowerBorder, int upperBorder, boolean useValue) {
        super(value, step, lowerBorder, upperBorder);
        this.useValue = useValue;
    }

    public boolean useValue() {
        return useValue;
    }

    @Override
    public void changeValue(int amount) {
        if (useValue) {
            super.changeValue(amount);
        }
    }

    public void setUseValue(boolean useValue) {
        this.useValue = useValue;
    }
}
