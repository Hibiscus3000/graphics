package ru.nsu.fit.icg.lab2.dialog.edit_box;

import javafx.beans.property.IntegerProperty;

public class OddIntegerSideEditBox extends IntegerValueEditBox {

    public OddIntegerSideEditBox(String valueName, IntegerProperty integerProperty,
                                 int min, int max) {
        super(valueName, integerProperty, min, max, 2);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
    }
}
