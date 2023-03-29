package ru.nsu.fit.icg.lab2.filter.convolution;

public class EmbossingFilter extends ConvolutionFilter {
    @Override
    public String getName() {
        return "Тиснение";
    }

    @Override
    public String getJsonName() {
        return "embossing";
    }
}
