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
    private final Property<T> property;

    public NumberTextField(String valueName, Property<T> property,
                           T min, T max, T step) {
        this.min = min;
        this.max = max;
        this.step = step;
        this.property = property;

        HBox textFieldBox = new HBox();
        TextField textField = new TextField();
        Bindings.bindBidirectional(textField.textProperty(), property,
                getStringConverter());
        Button decrementButton = new Button("-");
        decrementButton.setOnAction(e -> {
            e.consume();
            decrement();
        });
        Button incrementButton = new Button("+");
        incrementButton.setOnAction(e -> {
            e.consume();
            increment();
        });
        textFieldBox.getChildren().addAll(decrementButton, textField, incrementButton);
        getChildren().addAll(new Label(valueName), textFieldBox);
    }

    protected abstract StringConverter<T> getStringConverter();

    protected abstract void increment();

    protected abstract void decrement();

}
