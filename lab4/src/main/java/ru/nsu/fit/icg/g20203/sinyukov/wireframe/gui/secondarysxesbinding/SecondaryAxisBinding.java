package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.secondarysxesbinding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;

public class SecondaryAxisBinding extends DoubleBinding {

    private final DoubleBinding sizeProperty;
    private final DoubleBinding centerBinding;
    private final DoubleProperty scaleProperty;
    private final int secondaryAxisId;
    private final int numberOfAxisTotal;

    public SecondaryAxisBinding(DoubleBinding sizeProperty,
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
