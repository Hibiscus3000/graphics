package ru.nsu.fit.icg.lab2.filter.neighborhood;

import javafx.scene.image.PixelReader;

import java.util.Arrays;

public class MedianFilter extends NeighborhoodFilter {

    private final int neighborhoodSide = 13;
    private final int neighborhoodSize = neighborhoodSide * neighborhoodSide;

    @Override
    protected int filterPixel(int x0, int y0, PixelReader pixelReader) {
        int halfNeighborhoodSide = neighborhoodSide / 2;
        int outOfBoundsColumns = -Math.min(Math.min(x0 - halfNeighborhoodSide, 0),
                Math.min(width - 1 - x0 - halfNeighborhoodSide, 0));
        int outOfBoundsLines = -Math.min(Math.min(y0 - halfNeighborhoodSide, 0),
                Math.min(height - 1 - y0 - halfNeighborhoodSide, 0));
        int currentNeighborhoodSize = neighborhoodSize
                - outOfBoundsColumns * neighborhoodSide
                - (neighborhoodSide - outOfBoundsColumns) * outOfBoundsLines;
        int[] red = new int[currentNeighborhoodSize],
                green = new int[currentNeighborhoodSize],
                blue = new int[currentNeighborhoodSize];
        int i = 0;
        int startX = Math.max(x0 - left, 0), endX = Math.min(x0 + right, width - 1);
        int startY = Math.max(y0 - left, 0), endY = Math.min(y0 + right, height - 1);
        for (int x = startX; x <= endX; ++x) {
            for (int y = startY; y <= endY; ++y) {
                int color = pixelReader.getArgb(x, y);
                red[i] = color >> 16 & 255;
                green[i] = color >> 8 & 255;
                blue[i++] = color & 255;
            }
        }
        Arrays.sort(red);
        Arrays.sort(green);
        Arrays.sort(blue);
        int halfNeighborhoodSize = currentNeighborhoodSize / 2;
        return 255 << 24
                | red[halfNeighborhoodSize] << 16
                | green[halfNeighborhoodSize] << 8
                | blue[halfNeighborhoodSize];
    }

    @Override
    protected int getLeft() {
        return neighborhoodSide / 2;
    }

    @Override
    protected int getRight() {
        return neighborhoodSide / 2;
    }

    @Override
    public String getName() {
        return "Медианный фильтр";
    }

    @Override
    public String getJsonName() {
        return "median";
    }
}
