package ru.nsu.fit.icg.lab2.dialog.editBox;

import javafx.beans.property.Property;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class ValueEditBox<T extends Number> extends VBox {

    protected final Spinner<T> spinner = new Spinner<>();
    protected final Slider slider;

    private final Property<T> property;
    protected T prevValue;

    public ValueEditBox(String labelText, Property<T> property,
                        T min, T max, T amountToStepBy) {
        getStyleClass().add("edit-box");

        this.property = property;
        prevValue = property.getValue();

        HBox sliderSpinnerBox = new HBox();
        sliderSpinnerBox.getStyleClass().add("slider-spinner-box");
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

        spinner.setMaxWidth(75);
        slider.setMinWidth(170);
    }

    public void ok() {
        prevValue = property.getValue();
    }

    public void cancel() {
        property.setValue(prevValue);
    }

    Alert savingFileErrorAlert;

    protected void formattingError() {
        cancel();
        if (null == savingFileErrorAlert) {
            savingFileErrorAlert = new Alert(Alert.AlertType.ERROR,
                    "Значение данного поля должно быть " + getNumberName(), ButtonType.OK);
            String errorTitle = "Ошибка форматирования";
            savingFileErrorAlert.setTitle(errorTitle);
            savingFileErrorAlert.setHeaderText(errorTitle);
        }
        if (!savingFileErrorAlert.isShowing()) {
            savingFileErrorAlert.showAndWait();
        }
    }

    protected abstract String getNumberName();
}
