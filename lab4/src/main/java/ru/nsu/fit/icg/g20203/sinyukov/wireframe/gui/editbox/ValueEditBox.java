package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox;

import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class ValueEditBox<T extends Number> extends VBox {

    protected final Spinner<T> spinner = new Spinner<>();
    protected final Slider slider;

    private final Property<T> property;

    public ValueEditBox(String labelText, Property<T> property, T min, T max, T amountToStepBy) {
        this.property = property;

        HBox sliderSpinnerBox = new HBox();
        slider = new Slider(min.doubleValue(), max.doubleValue(), property.getValue().doubleValue());
        slider.setBlockIncrement(Double.parseDouble(String.valueOf(amountToStepBy)));
        spinner.setEditable(true);
        spinner.valueProperty().addListener((observable, oldVal, newVal) -> {
            if (!slider.isValueChanging()) {
                slider.setValue(newVal.doubleValue());
            }
            property.setValue(newVal);
        });
        property.addListener((observable, oldVal, newVal) ->
                spinner.getValueFactory().valueProperty().set((T) newVal));
        sliderSpinnerBox.getChildren().addAll(slider, spinner);
        getChildren().addAll(new Label(labelText), sliderSpinnerBox);
    }
}
