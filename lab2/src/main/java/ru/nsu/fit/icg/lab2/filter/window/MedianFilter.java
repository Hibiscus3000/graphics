package ru.nsu.fit.icg.lab2.filter.window;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.PixelReader;

import java.util.Arrays;

public class MedianFilter extends WindowFilter {

    private final int minWindowSide = 3;
    private final int maxWindowSide = 15;
    private SimpleIntegerProperty windowSideProperty = new SimpleIntegerProperty(5);
    private int windowSize = windowSideProperty.get() * windowSideProperty.get();

    public MedianFilter() {
        windowSideProperty.addListener(((observable, oldVal, newVal) -> {
            if (0 == (int) newVal % 2) {
                windowSideProperty.set(Math.min((int) newVal + 1, maxWindowSide));
            } else {
                windowSize = windowSideProperty.get() * windowSideProperty.get();
            }
        }));
    }

    @Override
    protected int filterPixel(int x0, int y0, PixelReader pixelReader) {
        int windowSideVal = windowSideProperty.get();
        int halfWindowSide = windowSideVal / 2;
        int outOfBoundsColumns = -Math.min(Math.min(x0 - halfWindowSide, 0),
                Math.min(width - 1 - x0 - halfWindowSide, 0));
        int outOfBoundsLines = -Math.min(Math.min(y0 - halfWindowSide, 0),
                Math.min(height - 1 - y0 - halfWindowSide, 0));
        int currentWindowSize = windowSize
                - outOfBoundsColumns * windowSideVal
                - (windowSideVal - outOfBoundsColumns) * outOfBoundsLines;
        int[] red = new int[currentWindowSize],
                green = new int[currentWindowSize],
                blue = new int[currentWindowSize];
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
        int halfWindowSize = currentWindowSize / 2;
        return 255 << 24
                | red[halfWindowSize] << 16
                | green[halfWindowSize] << 8
                | blue[halfWindowSize];
    }

    public IntegerProperty windowSideProperty() {
        return windowSideProperty;
    }

    public int getMinWindowSide() {
        return minWindowSide;
    }

    public int getMaxWindowSide() {
        return maxWindowSide;
    }

    @Override
    protected int getLeft() {
        return windowSideProperty.get() / 2;
    }

    @Override
    protected int getRight() {
        return getLeft();
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
