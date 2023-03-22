package ru.nsu.fit.icg.lab2.filter.matrix;

import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class MatrixFilter implements Filter {

    private int[][] matrix;
    private int divider;

    @Override
    public WritableImage filter(WritableImage original) {
        return null;
    }
}
