package ru.nsu.fit.icg.lab2.dialog.editBox;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class DoubleValueEditBox extends ValueEditBox<Double> {

    public DoubleValueEditBox(String valueName, DoubleProperty doubleProperty,
                              Double min, Double max, Double amountToStepBy) {
        super(valueName, doubleProperty.asObject(), min, max, amountToStepBy);
        slider.valueProperty().addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().setValue((Double) newVal));
        spinner.setValueFactory(new DoubleSpinnerValueFactory(min, max,
                doubleProperty.getValue(), amountToStepBy));
    }

    @Override
    protected String getNumberName() {
        return "вещественным числом";
    }

    private class DoubleSpinnerValueFactory extends SpinnerValueFactory.DoubleSpinnerValueFactory {

        public DoubleSpinnerValueFactory(double min, double max, double initialValue, double amountToStepBy) {
            super(min, max, initialValue, amountToStepBy);
            setConverter(new DoubleStringConverter());
        }

        @Override
        public void increment(final int steps) {
            final double max = getMax();
            final double currentValue = getValue();
            final double newIndex = currentValue + steps * getAmountToStepBy();
            setValue(Math.min(newIndex, max));
        }

        @Override
        public void decrement(int steps) {
            final double min = getMin();
            final double currentValue = getValue();
            final double newIndex = currentValue - steps * getAmountToStepBy();
            setValue(Math.max(min, newIndex));
        }
    }

    private class DoubleStringConverter extends StringConverter<Double> {

        private final MathContext mathContext = new MathContext(3, RoundingMode.HALF_EVEN);
        private final int scale = 2;

        @Override
        public String toString(Double aDouble) {
            return new BigDecimal(aDouble, mathContext).setScale(scale, RoundingMode.HALF_EVEN)
                    .toString();
        }

        @Override
        public Double fromString(String s) {
            try {
                return new BigDecimal(s, mathContext).setScale(scale, RoundingMode.HALF_EVEN)
                        .doubleValue();
            } catch (NumberFormatException ex) {
                formattingError();
                spinner.getValueFactory().setValue(-1.0);
                return prevValue;
            }
        }
    }
}
