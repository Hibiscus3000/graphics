package ru.nsu.fit.icg.lab2.filter.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.GammaFilter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class GammaDialog extends FilterDialog {

    private final Spinner<Double> spinner = new Spinner();
    private final double amountToStepBy = 0.1;
    private final double min;
    private final double max;

    public GammaDialog(Filter filter) {
        super(filter);
        GammaFilter gammaFilter = (GammaFilter) filter;
        prevGamma = gammaFilter.getGamma();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);

        HBox gammaBox = new HBox();
        gammaBox.setSpacing(spacing);
        min = amountToStepBy;
        max = 10;
        Slider slider = new Slider(min, max, prevGamma);
        slider.setBlockIncrement(amountToStepBy);
        spinner.setEditable(true);
        spinner.setValueFactory(new GammaSpinnerValueFactory(min, max, prevGamma, amountToStepBy));
        spinner.valueProperty().addListener(e -> gammaFilter.setGamma(spinner.getValue()));
        slider.valueProperty().asObject().bindBidirectional(spinner.getValueFactory().valueProperty());
        gammaBox.getChildren().addAll(slider, spinner);

        vBox.getChildren().addAll(new Label("Гамма"), gammaBox, getButtonBox());
        getDialogPane().setContent(vBox);
    }

    private double prevGamma;

    @Override
    protected void cancel() {
        ((GammaFilter) filter).setGamma(prevGamma);
    }

    private class GammaSpinnerValueFactory extends SpinnerValueFactory.DoubleSpinnerValueFactory {

        public GammaSpinnerValueFactory(double min, double max, double initialValue, double amountToStepBy) {
            super(min, max, initialValue, amountToStepBy);
            setConverter(new StringConverter<Double>() {
                private final MathContext mathContext = new MathContext(3, RoundingMode.HALF_EVEN);
                private final int scale = 2;

                @Override
                public String toString(Double aDouble) {
                    return new BigDecimal(aDouble, mathContext).setScale(scale, RoundingMode.HALF_EVEN)
                            .toString();
                }

                @Override
                public Double fromString(String s) {
                    return new BigDecimal(s, mathContext).setScale(scale, RoundingMode.HALF_EVEN)
                            .doubleValue();
                }
            });
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
