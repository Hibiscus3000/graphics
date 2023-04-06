package ru.nsu.fit.icg.lab2.filter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.WritableImage;

public abstract class Filter {

    private final SimpleBooleanProperty isSelected = new SimpleBooleanProperty(false);

    public SimpleBooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public abstract WritableImage filter(WritableImage original);

    public abstract String getName();

    public abstract String getJsonName();
}
