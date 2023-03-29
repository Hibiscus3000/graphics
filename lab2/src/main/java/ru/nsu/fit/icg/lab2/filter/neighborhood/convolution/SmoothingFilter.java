package ru.nsu.fit.icg.lab2.filter.neighborhood.convolution;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;
import java.util.Map;

public class SmoothingFilter extends ConvolutionFilter {

    private final IntegerProperty matrixSideProperty;
    private final Map<Integer, Integer[][]> matricesMap = new HashMap<>(); // matrixSide => matrix
    private final Map<Integer, Integer> dividersMap = new HashMap<>(); // matrixSide => divider

    public SmoothingFilter() {
        matrixSideProperty = new SimpleIntegerProperty(3);
        matrixSideProperty.addListener((observable, oldVal, newVal) -> {
            Integer newValue = (Integer) newVal;
            if (0 == newValue % 2) {
                matrixSideProperty.set(Math.min(newValue + 1, 11));
            }
            recalculateMatrix(newValue);
        });
        recalculateMatrix(matrixSideProperty.get());
    }

    private void recalculateMatrix(Integer matrixSide) {
        Integer[][] matrix = matricesMap.get(matrixSide);
        Integer divider = dividersMap.get(matrixSide);
        if (null == matrix) {
            if (3 == matrixSide) {
                matrix = new Integer[][]{
                        {null, 1, null},
                        {1, 2, 1},
                        {null, 1, null}};
                divider = 6;
            } else if (5 == matrixSide) {
                matrix = new Integer[][]{
                        {1, 2, 3, 2, 1},
                        {2, 4, 5, 4, 2},
                        {3, 5, 6, 5, 3},
                        {2, 4, 5, 4, 2},
                        {1, 2, 3, 2, 1}};
                divider = 74;
            } else {
                matrix = new Integer[matrixSide][matrixSide];
                for (int i = 0; i < matrixSide; ++i) {
                    for (int j = 0; j < matrixSide; ++j) {
                        matrix[i][j] = 1;
                    }
                }
                divider = matrixSide * matrixSide;
            }
            matricesMap.put(matrixSide, matrix);
            dividersMap.put(matrixSide, divider);
        }
        this.matrix = matrix;
        this.divider = divider;
    }

    public int getMatrixSide() {
        return matrixSideProperty.get();
    }

    public IntegerProperty matrixSideProperty() {
        return matrixSideProperty;
    }

    @Override
    public String getName() {
        return "Сглаживание";
    }

    @Override
    public String getJsonName() {
        return "smoothing";
    }
}
