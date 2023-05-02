package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public abstract class NumberTextField<T extends Number> extends VBox {

    protected final T min;
    protected final T max;
    protected final T step;
    protected Property<T> property;
    private final StringConverter<T> stringConverter;

    private final Button decrementButton;
    private final Button incrementButton;
    protected final TextField textField = new TextField();

    public NumberTextField(String valueName, Property<T> property,
                           T min, T max, T step) {
        this.min = min;
        this.max = max;
        this.step = step;

        HBox textFieldBox = new HBox();
        stringConverter = getStringConverter();
        decrementButton = new Button("-");
        decrementButton.setOnAction(e -> {
            e.consume();
            decrement();
        });
        incrementButton = new Button("+");
        incrementButton.setOnAction(e -> {
            e.consume();
            increment();
        });
        if (null != property) {
            bind(property);
        } else {
            unbind();
        }
        textFieldBox.getChildren().addAll(decrementButton, textField, incrementButton);
        getChildren().addAll(new Label(valueName), textFieldBox);
    }

    protected abstract StringConverter<T> getStringConverter();

    protected abstract void increment();

    protected abstract void decrement();

    public void unbind() {
        if (null != property) {
            textField.textProperty().unbindBidirectional(property);
            property = null;
        }
        textField.setText("");
        textField.setDisable(true);
        decrementButton.setDisable(true);
        incrementButton.setDisable(true);
    }

    public void bind(Property<T> property) {
        unbind();
        if (null != property) {
            this.property = property;
            Bindings.bindBidirectional(textField.textProperty(), property, stringConverter);
            textField.setDisable(false);
            decrementButton.setDisable(false);
            incrementButton.setDisable(false);
        }
    }

}
