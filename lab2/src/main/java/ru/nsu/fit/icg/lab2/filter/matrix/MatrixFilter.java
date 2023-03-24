package ru.nsu.fit.icg.lab2.filter.matrix;

import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class MatrixFilter implements Filter {

    protected int matrixWidth;
    protected int matrixHeight;
    protected int[][] matrix;
    private int divider;

    protected void setMatrixSize(int matrixWidth, int matrixHeight) {
        this.matrixWidth = matrixWidth;
        this.matrixHeight = matrixHeight;
        matrix = new int[matrixHeight][matrixWidth];
    }

    @Override
    public WritableImage filter(WritableImage original) {
        return null;
    }
}
