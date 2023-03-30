package ru.nsu.fit.icg.lab2.filter;

public abstract class Matrix {

    protected int[][] matrix;
    protected boolean changed;

    public abstract int[][] getMatrix();

    public abstract void setPreferredSide(Integer matrixPreferredSide);

    public abstract Integer[] getSides();

    public abstract Integer getSide();

    public boolean сhanged() {
        return changed;
    }

    public void setChanged(boolean b) {
        changed = b;
    }
}
