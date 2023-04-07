package ru.nsu.fit.icg.lab2.filter.window.convolution.typed;

public interface MatrixType {

    String getName();

    int[][] getMatrix();

    default String getImageName() {
        return null;
    }
}
