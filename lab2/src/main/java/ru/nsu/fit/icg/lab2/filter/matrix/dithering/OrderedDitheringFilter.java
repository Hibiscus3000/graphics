package ru.nsu.fit.icg.lab2.filter.matrix.dithering;

import ru.nsu.fit.icg.lab2.filter.matrix.MatrixFilter;

public class OrderedDitheringFilter extends MatrixFilter {

    @Override
    public String getName() {
        return "Упорядоченный дизеринг";
    }

    @Override
    public String getJsonName() {
        return "orderedDithering";
    }
}
