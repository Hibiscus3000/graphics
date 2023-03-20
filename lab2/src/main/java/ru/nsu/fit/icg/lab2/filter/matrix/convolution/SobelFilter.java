package ru.nsu.fit.icg.lab2.filter.matrix.convolution;

public class SobelFilter extends ConvolutionFilter {

    @Override
    public String getName() {
        return "Фильтр Собеля";
    }

    @Override
    public String getJsonName() {
        return "sobel";
    }
}
