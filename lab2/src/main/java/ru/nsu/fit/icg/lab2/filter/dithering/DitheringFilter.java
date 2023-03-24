package ru.nsu.fit.icg.lab2.filter.dithering;

import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class DitheringFilter implements Filter {

    public enum Color {
        RED, GREEN, BLUE
    }

    protected int[] colorQuantization = new int[]{8, 8, 8};

    public abstract boolean setQuantization(Color color, int quantization);

    public int getQuantization(Color color) {
        return colorQuantization[color.ordinal()];
    }
}
