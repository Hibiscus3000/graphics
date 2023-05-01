package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;

public class SecondaryAxisCoordinateBinding extends DoubleBinding {

    private final ReadOnlyDoubleProperty sizeProperty;
    private final DoubleBinding centerBinding;
    private final DoubleProperty scaleProperty;
    private final int secondaryAxisId;
    private final int numberOfAxisTotal;

    public SecondaryAxisCoordinateBinding(ReadOnlyDoubleProperty sizeProperty,
                                          DoubleBinding centerBinding,
                                          DoubleProperty scaleProperty,
                                          int secondaryAxisId,
                                          int numberOfAxisTotal) {
        this.sizeProperty = sizeProperty;
        this.centerBinding = centerBinding;
        this.scaleProperty = scaleProperty;
        bind(sizeProperty, centerBinding, scaleProperty);
        this.secondaryAxisId = secondaryAxisId;
        this.numberOfAxisTotal = numberOfAxisTotal;
    }

    @Override
    protected double computeValue() {
        double size = sizeProperty.get();
        return (size + (size * ((double) secondaryAxisId / numberOfAxisTotal)
                + centerBinding.get() * scaleProperty.get()) % size) % size;
    }
}
