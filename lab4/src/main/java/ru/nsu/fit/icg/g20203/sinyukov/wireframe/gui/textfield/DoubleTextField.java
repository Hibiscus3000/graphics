package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield;

import javafx.beans.property.DoubleProperty;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class DoubleTextField extends NumberTextField {

    public DoubleTextField(String valueName, DoubleProperty DoubleProperty,
                           double min, double max, double step) {
        super(valueName, DoubleProperty, min, max, step);
    }

    @Override
    protected StringConverter getStringConverter() {
        return new DoubleStringConverter();
    }

    @Override
    protected void increment() {
        DoubleProperty doubleProperty = (DoubleProperty) property;
        if (null != doubleProperty) {
            doubleProperty.set(Math.min(doubleProperty.get() + step.doubleValue(), max.doubleValue()));
        }
    }

    @Override
    protected void decrement() {
        DoubleProperty doubleProperty = (DoubleProperty) property;
        if (null != doubleProperty) {
            doubleProperty.set(Math.max(min.doubleValue(), doubleProperty.get() - step.doubleValue()));
        }
    }
}
