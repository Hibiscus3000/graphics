package ru.nsu.fit.icg.lab2.filter.matrix.convolution;

public class SmoothingFilter extends ConvolutionFilter {
    @Override
    public String getName() {
        return "Сглаживание";
    }

    @Override
    public String getJsonName() {
        return "smoothing";
    }
}
