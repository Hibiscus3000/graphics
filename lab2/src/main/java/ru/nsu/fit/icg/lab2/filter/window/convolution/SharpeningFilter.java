package ru.nsu.fit.icg.lab2.filter.window.convolution;

public class SharpeningFilter extends ConvolutionFilter {

    public SharpeningFilter() {
        matrix = new Integer[][]{
                {-1, -1, -1},
                {-1, 9, -1},
                {-1, -1, -1}};
        divider = 1;
    }

    @Override
    public String getName() {
        return "Увеличение резкости";
    }

    @Override
    public String getJsonName() {
        return "sharpening";
    }
}
