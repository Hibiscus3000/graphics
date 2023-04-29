package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.DoubleStringConverter;

public class DoubleValueEditBox extends ValueEditBox<Double> {

    public DoubleValueEditBox(String valueName, DoubleProperty doubleProperty,
                              Double min, Double max, Double amountToStepBy) {
        super(valueName, doubleProperty.asObject(), min, max, amountToStepBy);
        slider.valueProperty().addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().setValue((Double) newVal));
        spinner.setValueFactory(new DoubleSpinnerValueFactory(min, max,
                doubleProperty.getValue(), amountToStepBy));
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
}

