package ru.nsu.fit.icg.lab2.filter.dithering;

import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class DitheringFilter implements Filter {

    protected int[] colorQuantization = new int[]{8, 8, 8};

    protected int getNearestPaletteColor(int color, int quantizationNumber) {
        final int colorDistance = 255 / (quantizationNumber - 1);
        int colorQuanta = color / colorDistance;
        final int lesser = colorDistance * colorQuanta;
        final int bigger = Math.min(colorDistance * quantizationNumber,
                colorDistance * (colorQuanta + 1));
        return color - lesser > bigger - color ? bigger : lesser;
    }

    public abstract boolean setQuantization(Color color, int quantization);

    public int getQuantization(Color color) {
        return colorQuantization[color.ordinal()];
    }
}
