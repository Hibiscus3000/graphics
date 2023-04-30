package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.converter.IntegerConverter;

public class IntegerTextField extends VBox {

    private final int min;
    private final int max;
    private final int step;
    private final IntegerProperty integerProperty;

    public IntegerTextField(String valueName, IntegerProperty integerProperty,
                            int min, int max, int step) {
        this.min = min;
        this.max = max;
        this.step = step;
        this.integerProperty = integerProperty;

        HBox textFieldBox = new HBox();
        TextField textField = new TextField();
        Bindings.bindBidirectional(textField.textProperty(),integerProperty,new NumberStringConverter());
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
        getChildren().addAll(new Label(valueName),textFieldBox);
    }

    private void increment() {
        integerProperty.set(Math.min(integerProperty.get() + step, max));
    }

    private void decrement() {
        integerProperty.set(Math.max(min, integerProperty.get() - step));
    }
}
