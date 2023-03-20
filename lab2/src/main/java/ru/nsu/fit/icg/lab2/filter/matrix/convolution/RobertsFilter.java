package ru.nsu.fit.icg.lab2.filter.matrix.convolution;

public class RobertsFilter extends ConvolutionFilter {
    @Override
    public String getName() {
        return "Фильтр Робертса";
    }

    @Override
    public String getJsonName() {
        return "roberts";
    }
}
