package ru.nsu.fit.icg.lab2.filter.dithering.ordered;

import javafx.beans.property.SimpleIntegerProperty;

public class OrdDitherQuantProperty extends SimpleIntegerProperty {

    private final OrderedDitheringMatrix orderedDitheringMatrix;

    public OrdDitherQuantProperty(OrderedDitheringMatrix orderedDitheringMatrix) {
        this.orderedDitheringMatrix = orderedDitheringMatrix;
        set(4);
        addListener(((observableValue, oldVal, newVal) ->
                orderedDitheringMatrix.setBoundColorQuantization((Integer) newVal)));
    }
}
