package ru.nsu.fit.icg.lab2.filter.neighborhood.convolution;

public class MotionFilter extends ConvolutionFilter {

    public MotionFilter() {
        divider = 9;
        matrix = new Integer[divider][divider];
        for (int i = 0; i < divider; ++i) {
            for (int j = 0; j < divider; ++j) {
                matrix[i][j] = i == j ? 1 : 0;
            }
        }
    }

    @Override
    public String getName() {
        return "Фильтр движения";
    }

    @Override
    public String getJsonName() {
        return "motion";
    }
}
