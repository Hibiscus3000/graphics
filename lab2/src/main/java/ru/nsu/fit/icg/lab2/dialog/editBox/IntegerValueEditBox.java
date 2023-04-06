package ru.nsu.fit.icg.lab2.dialog.editBox;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

public class IntegerValueEditBox extends ValueEditBox<Integer> {

    public IntegerValueEditBox(String valueName, IntegerProperty integerProperty,
                               Integer min, Integer max, Integer amountToStepBy) {
        super(valueName, integerProperty.asObject(), min, max, amountToStepBy);
        slider.valueProperty().addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().setValue(newVal.intValue()));
        spinner.setValueFactory(new IntSpinnerValueFactory(min, max,
                integerProperty.getValue(), amountToStepBy));
    }

    @Override
    protected String getNumberName() {
        return "целым числом";
    }

    private class IntSpinnerValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

        public IntSpinnerValueFactory(int min, int max, int initialValue, int amountToStepBy) {
            super(min, max, initialValue, amountToStepBy);
            setConverter(new IntegerConverter());
        }

        @Override
        public void increment(final int steps) {
            final int max = getMax();
            final int currentValue = getValue();
            final int newIndex = currentValue + steps * getAmountToStepBy();
            setValue(Math.min(newIndex, max));
        }

        @Override
        public void decrement(int steps) {
            final int min = getMin();
            final int currentValue = getValue();
            final int newIndex = currentValue - steps * getAmountToStepBy();
            setValue(Math.max(min, newIndex));
        }
    }

    private class IntegerConverter extends StringConverter<Integer> {

        @Override
        public String toString(Integer integer) {
            return integer.toString();
        }

        @Override
        public Integer fromString(String s) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                formattingError();
                spinner.getValueFactory().setValue(-1);
                return prevValue;
            }
        }
    }
}
