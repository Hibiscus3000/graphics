package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield;

import javafx.beans.property.IntegerProperty;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class IntegerTextField extends NumberTextField {

    public IntegerTextField(String valueName, IntegerProperty integerProperty,
                            int min, int max, int step) {
        super(valueName, integerProperty, min, max, step);
    }

    @Override
    protected StringConverter getStringConverter() {
        return new IntegerStringConverter();
    }

    @Override
    protected void increment() {
        IntegerProperty integerProperty = (IntegerProperty) property;
        if (null != integerProperty) {
            integerProperty.set(Math.min(integerProperty.get() + step.intValue(), max.intValue()));
        }
    }

    @Override
    protected void decrement() {
        IntegerProperty integerProperty = (IntegerProperty) property;
        if (null != integerProperty) {
            integerProperty.set(Math.max(min.intValue(), integerProperty.get() - step.intValue()));
        }
    }
}
