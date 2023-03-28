package ru.nsu.fit.icg.lab2.filter.dithering;

import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;

public abstract class DitheringFilter extends ThreeColorFilter {

    protected int getNearestPaletteColor(int color, int quantizationNumber) {
        final int colorDistance = 255 / (quantizationNumber - 1);
        int colorQuanta = color / colorDistance;
        final int lesser = colorDistance * colorQuanta;
        final int bigger = Math.min(colorDistance * quantizationNumber,
                colorDistance * (colorQuanta + 1));
        return color - lesser > bigger - color ? bigger : lesser;
    }
}
