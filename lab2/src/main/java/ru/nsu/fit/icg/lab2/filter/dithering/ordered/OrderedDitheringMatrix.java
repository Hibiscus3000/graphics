package ru.nsu.fit.icg.lab2.filter.dithering.ordered;

import javafx.beans.property.IntegerProperty;
import ru.nsu.fit.icg.lab2.filter.Matrix;

public class OrderedDitheringMatrix extends Matrix {

    private final IntegerProperty boundColorQuantization;
    private Integer matrixPreferredSide;
    private int matrixSide;

    OrderedDitheringMatrix(Integer matrixPreferredSide, IntegerProperty boundColorQuantization) {
        this.matrixPreferredSide = matrixPreferredSide;
        this.boundColorQuantization = boundColorQuantization;
        recalculateMatrix(boundColorQuantization.get());
        boundColorQuantization.addListener(((observableValue, oldValue, newValue) -> recalculateMatrix(newValue.intValue())));
        ;
    }

    @Override
    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public void setPreferredSide(Integer matrixPreferredSide) {
        this.matrixPreferredSide = matrixPreferredSide;
        recalculateMatrix(boundColorQuantization.get());
    }

    @Override
    public Integer[] getSides() {
        return new Integer[]{2, 4, 8, 16, null};
    }

    @Override
    public Integer getSide() {
        return matrixSide;
    }

    public void recalculateMatrix(int boundColorQuantization) {
        int matrixSide;
        if (null == this.matrixPreferredSide) {
            matrixSide = getMatrixSideAuto(boundColorQuantization);
        } else {
            matrixSide = this.matrixPreferredSide;
        }
        if (matrixSide != this.matrixSide) {
            matrix = OrderedDitheringFilter.getMatrix(matrixSide);
            this.matrixSide = matrixSide;
            changed = true;
        }
    }

    private int getMatrixSideAuto(int boundColorQuantization) {
        if (boundColorQuantization <= 4) {
            return 16;
        }
        if (boundColorQuantization <= 16) {
            return 8;
        }
        if (boundColorQuantization <= 64) {
            return 4;
        }
        return 2;
    }
}
