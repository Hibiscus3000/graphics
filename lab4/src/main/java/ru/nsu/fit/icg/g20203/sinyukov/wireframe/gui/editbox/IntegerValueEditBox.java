package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.SpinnerValueFactory;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.converter.IntegerConverter;

public class IntegerValueEditBox extends ValueEditBox<Integer> {

    public IntegerValueEditBox(String valueName, IntegerProperty integerProperty,
                               Integer min, Integer max, Integer amountToStepBy) {
        super(valueName, integerProperty.asObject(), min, max, amountToStepBy);
        slider.valueProperty().addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().setValue(newVal.intValue()));
        spinner.setValueFactory(new IntSpinnerValueFactory(min, max,
                integerProperty.getValue(), amountToStepBy));
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


}
