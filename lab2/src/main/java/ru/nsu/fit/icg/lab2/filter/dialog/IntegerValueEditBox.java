package ru.nsu.fit.icg.lab2.filter.dialog;

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

    public IntegerValueEditBox(String valueName, int min, int max, int initialValue, int amountToStepBy) {
        setAlignment(Pos.CENTER);
        setSpacing(spacing);

        HBox sliderSpinnerBox = new HBox();
        sliderSpinnerBox.setSpacing(spacing);
        Slider slider = new Slider(min, max, initialValue);
        slider.setBlockIncrement(amountToStepBy);
        spinner.setEditable(true);
        spinner.setValueFactory(new IntValueSpinnerValueFactory(min, max, initialValue, amountToStepBy));
        slider.valueProperty().addListener(e -> spinner.getValueFactory().setValue((int) slider.getValue()));
        spinner.valueProperty().addListener(e -> slider.setValue(spinner.getValue()));
        sliderSpinnerBox.getChildren().addAll(slider, spinner);

        getChildren().addAll(new Label(valueName), sliderSpinnerBox);
    }

    public int getValue() {
        return spinner.getValue();
    }

    private class IntValueSpinnerValueFactory extends SpinnerValueFactory.IntegerSpinnerValueFactory {

        public IntValueSpinnerValueFactory(int min, int max, int initialValue, int amountToStepBy) {
            super(min, max, initialValue, amountToStepBy);
        }

        @Override
        public void increment(final int steps) {
            final int min = getMin();
            final int max = getMax();
            final int currentValue = getValue();
            final int newIndex = currentValue + steps * getAmountToStepBy();
            setValue(newIndex <= max ? newIndex : min + newIndex % getAmountToStepBy());
        }

        @Override
        public void decrement(int steps) {
            final int min = getMin();
            final int max = getMax();
            final int currentValue = getValue();
            final int newIndex = currentValue - steps * getAmountToStepBy();
            setValue(newIndex >= min ? newIndex : max - newIndex % getAmountToStepBy());
        }
    }
}