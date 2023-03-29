package ru.nsu.fit.icg.lab2.dialog;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IntegerValueEditBox extends VBox {

    private final static int spacing = 5;
    private final Spinner<Integer> spinner = new Spinner<>();
    protected final Slider slider;

    public IntegerValueEditBox(String valueName, IntegerProperty integerProperty,
                               int min, int max, int amountToStepBy) {
        setAlignment(Pos.CENTER);
        setSpacing(spacing);

        HBox sliderSpinnerBox = new HBox();
        sliderSpinnerBox.setSpacing(spacing);
        slider = new Slider(min, max, integerProperty.get());
        slider.setBlockIncrement(amountToStepBy);
        spinner.setEditable(true);
        spinner.setValueFactory(new IntSpinnerValueFactory(min, max, integerProperty.get(), amountToStepBy));
        slider.valueProperty().addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().setValue(newVal.intValue()));
        spinner.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (!slider.isValueChanging()) {
                slider.setValue(newVal);
            }
            integerProperty.set(newVal);
        });
        integerProperty.addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().valueProperty().set((Integer) newVal));
        sliderSpinnerBox.getChildren().addAll(slider, spinner);

        getChildren().addAll(new Label(valueName), sliderSpinnerBox);
    }

    public int getValue() {
        return spinner.getValue();
    }

    public void setValue(int value) {
        spinner.getValueFactory().setValue(value);
    }

    protected Integer previous;

    protected boolean setNewPrevious(Integer newVal) {
        boolean otherThenPrevious = otherThenPrevious(newVal);
        previous = newVal;
        return otherThenPrevious;
    }

    protected boolean otherThenPrevious(Integer newVal) {
        return !newVal.equals(previous);
    }

    private class IntSpinnerValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

        public IntSpinnerValueFactory(int min, int max, int initialValue, int amountToStepBy) {
            super(min, max, initialValue, amountToStepBy);
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
