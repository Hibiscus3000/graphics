package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.converter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class DoubleConverter extends Converter<Double> {

    private final MathContext mathContext = new MathContext(3, RoundingMode.HALF_EVEN);
    private final int scale = 2;

    @Override
    public String toString(Double aDouble) {
        return new BigDecimal(aDouble, mathContext).setScale(scale, RoundingMode.HALF_EVEN)
                .toString();
    }

    @Override
    public Double fromString(String s) {
        try {
            double newValue = new BigDecimal(s, mathContext).setScale(scale, RoundingMode.HALF_EVEN)
                    .doubleValue();
            prevValue = newValue;
            return newValue;
        } catch (NumberFormatException ex) {
            formattingError();
            return prevValue;
        }
    }

    @Override
    protected String getNumberName() {
        return "вещественным числом";
    }
}
