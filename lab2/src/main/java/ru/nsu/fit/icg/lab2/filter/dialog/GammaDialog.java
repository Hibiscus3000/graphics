package ru.nsu.fit.icg.lab2.filter.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.GammaFilter;

public class GammaDialog extends FilterDialog {

    public GammaDialog(Filter filter) {
        super(filter);
        GammaFilter gammaFilter = (GammaFilter) filter;
        double gamma = gammaFilter.getGamma();

        setTitle("Гамма-фильтр");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);

        HBox gammaBox = new HBox();
        gammaBox.setSpacing(spacing);
        Spinner<Double> spinner = new Spinner<>();
        Slider slider = new Slider(0.1, 10, 1);
        slider.setBlockIncrement(0.1);
        spinner.setEditable(true);
        spinner.setValueFactory(new GammaSpinnerValueFactory(0.1, 10, gamma, 0.1));
        slider.valueProperty().asObject().bindBidirectional(spinner.getValueFactory().valueProperty());
        gammaBox.getChildren().addAll(slider, spinner);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(spacing);
        Button okButton = new Button("Ок");
        okButton.setOnAction(e -> {
            gammaFilter.setGamma(slider.getValue());
            System.out.println(spinner.getValue());
            setResult(ButtonType.OK);
            hide();
            e.consume();
        });
        Button cancelButton = new Button("Отмена");
        cancelButton.setOnAction(e -> {
            hide();
            setResult(ButtonType.CANCEL);
            e.consume();
        });
        buttonBox.getChildren().addAll(okButton, cancelButton);

        vBox.getChildren().addAll(new Label("Гамма"), gammaBox, buttonBox);
        getDialogPane().setContent(vBox);
    }

    private class GammaSpinnerValueFactory extends SpinnerValueFactory.DoubleSpinnerValueFactory {

        public GammaSpinnerValueFactory(double min, double max, double initialValue, double amountToStepBy) {
            super(min, max, initialValue, amountToStepBy);
            setConverter(new DoubleStringConverter());
        }

        @Override
        public void increment(final int steps) {
            final double min = getMin();
            final double max = getMax();
            final double currentValue = getValue();
            final double newIndex = currentValue + steps * getAmountToStepBy();
            setValue(newIndex <= max ? newIndex : min + newIndex % getAmountToStepBy());
        }

        @Override
        public void decrement(int steps) {
            final double min = getMin();
            final double max = getMax();
            final double currentValue = getValue();
            final double newIndex = currentValue - steps * getAmountToStepBy();
            setValue(newIndex >= min ? newIndex : max - newIndex % getAmountToStepBy());
        }
    }
}
