package ru.nsu.fit.icg.lab2.filter.matrix;

public abstract class Matrix {
    protected int[][] matrix;

    public abstract int[][] getMatrix();

    public abstract boolean setPreferredSide(Integer matrixPreferredSide);

    public abstract Integer[] getSizes();

    public abstract Integer getSize();
}
