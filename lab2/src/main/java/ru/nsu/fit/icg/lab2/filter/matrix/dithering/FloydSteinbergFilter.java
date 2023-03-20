package ru.nsu.fit.icg.lab2.filter.matrix.dithering;

import ru.nsu.fit.icg.lab2.filter.matrix.MatrixFilter;

public class FloydSteinbergFilter extends MatrixFilter {

    @Override
    public String getName() {
        return "Дизеринг Флойда-Стейнберга";
    }

    @Override
    public String getJsonName() {
        return "floydSteinberg";
    }
}
