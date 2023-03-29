package ru.nsu.fit.icg.lab2.filter.dithering.ordered;

import ru.nsu.fit.icg.lab2.filter.Matrix;

public class OrderedDitheringMatrix extends Matrix {

    private int boundColorQuantization;
    private Integer matrixPreferredSide;
    private int matrixSide;

    OrderedDitheringMatrix(Integer matrixPreferredSide, int boundColorQuantization) {
        this.matrixPreferredSide = matrixPreferredSide;
        this.boundColorQuantization = boundColorQuantization;
        recalculateMatrix();
    }

    @Override
    public Integer[][] getMatrix() {
        return matrix;
    }

    @Override
    public void setPreferredSide(Integer matrixPreferredSide) {
        this.matrixPreferredSide = matrixPreferredSide;
        recalculateMatrix();
    }

    public void setBoundColorQuantization(int boundColorQuantization) {
        this.boundColorQuantization = boundColorQuantization;
        recalculateMatrix();
    }

    @Override
    public Integer[] getSides() {
        return new Integer[]{2, 4, 8, 16, null};
    }

    @Override
    public Integer getSide() {
        return matrixSide;
    }

    public void recalculateMatrix() {
        int matrixSide;
        if (null == this.matrixPreferredSide) {
            matrixSide = getMatrixSideAuto();
        } else {
            matrixSide = this.matrixPreferredSide;
        }
        if (matrixSide != this.matrixSide) {
            matrix = OrderedDitheringFilter.getMatrix(matrixSide);
            this.matrixSide = matrixSide;
            changed = true;
        }
    }

    private int getMatrixSideAuto() {
        if (boundColorQuantization <= 4) {
            return 2;
        }
        if (boundColorQuantization <= 16) {
            return 4;
        }
        if (boundColorQuantization <= 64) {
            return 8;
        }
        return 16;
    }
}
