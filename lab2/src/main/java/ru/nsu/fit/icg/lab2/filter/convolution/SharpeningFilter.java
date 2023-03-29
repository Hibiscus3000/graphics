package ru.nsu.fit.icg.lab2.filter.convolution;

public class SharpeningFilter extends ConvolutionFilter {

    @Override
    public String getName() {
        return "Увеличение резкости";
    }

    @Override
    public String getJsonName() {
        return "sharpening";
    }
}
