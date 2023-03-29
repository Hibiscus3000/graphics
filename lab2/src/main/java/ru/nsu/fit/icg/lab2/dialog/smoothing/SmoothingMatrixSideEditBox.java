package ru.nsu.fit.icg.lab2.dialog.smoothing;

import javafx.beans.property.IntegerProperty;
import ru.nsu.fit.icg.lab2.dialog.IntegerValueEditBox;

public class SmoothingMatrixSideEditBox extends IntegerValueEditBox {

    public SmoothingMatrixSideEditBox(IntegerProperty matrixSideProperty) {
        super("Размер матрицы сглаживания", matrixSideProperty, 3, 11, 2);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
    }

    @Override
    protected boolean otherThenPrevious(Integer newVal) {
        return super.otherThenPrevious(newVal) && 1 == newVal % 2;
    }
}
