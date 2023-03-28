package ru.nsu.fit.icg.lab2.filter;

import javafx.beans.property.IntegerProperty;

public abstract class ThreeColorFilter implements Filter {

    protected IntegerProperty[] colorProperties;

    public IntegerProperty getColorProperty(Color color) {
        return colorProperties[color.ordinal()];
    }
}
