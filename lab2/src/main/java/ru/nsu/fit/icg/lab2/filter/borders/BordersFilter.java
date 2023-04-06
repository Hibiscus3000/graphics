package ru.nsu.fit.icg.lab2.filter.borders;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.BlackWhiteFilter;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class BordersFilter extends Filter {

    private final SimpleIntegerProperty colorBinarization;
    private final SimpleBooleanProperty blackWhiteBorders = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty sharpBorders = new SimpleBooleanProperty(false);

    private final int[][] first;
    private final int[][] second;
    private final int divider;

    protected BordersFilter(int[][] first, int[][] second, int divider, int defaultBinarization) {
        this.first = first;
        this.second = second;
        this.divider = divider;
        colorBinarization = new SimpleIntegerProperty(defaultBinarization);
    }

    private int width;
    private int height;

    @Override
    public WritableImage filter(WritableImage original) {
        width = (int) original.getWidth();
        height = (int) original.getHeight();
        WritableImage filteredImage = new WritableImage(width, height);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelWriter.setArgb(x, y, filterPixel(x, y, pixelReader));
            }
        }
        return filteredImage;
    }

    private int filterPixel(int x0, int y0, PixelReader pixelReader) {
        int redSecond = 0, greenSecond = 0, blueSecond = 0;
        int redFirst = 0, greenFirst = 0, blueFirst = 0;
        int left = first.length >> 1, right = (first.length >> 1) - (first.length & 1 ^ 1);
        for (int x = x0 - left; x <= x0 + right; ++x) {
            for (int y = y0 - left; y <= y0 + right; ++y) {
                int color = pixelReader.getArgb(Math.max(0, Math.min(x, width - 1)),
                        Math.max(0, Math.min(y, height - 1)));
                int r = color >> 16 & 255, g = color >> 8 & 255, b = color & 255;
                int matrixX = x - x0 + left, matrixY = y - y0 + left;
                redFirst += first[matrixX][matrixY] * r;
                greenFirst += first[matrixX][matrixY] * g;
                blueFirst += first[matrixX][matrixY] * b;
                redSecond += second[matrixX][matrixY] * r;
                greenSecond += second[matrixX][matrixY] * g;
                blueSecond += second[matrixX][matrixY] * b;
            }
        }
        int red = Math.abs(redFirst) + Math.abs(redSecond);
        int green = Math.abs(greenFirst) + Math.abs(greenSecond);
        int blue = Math.abs(blueFirst) + Math.abs(blueSecond);
        if (blackWhiteBorders.get()) {
            int blackWhite = BlackWhiteFilter.getBlackWhite(red / divider,
                    green / divider,
                    blue / divider);
            int result = getColorValue(divider * blackWhite);
            return 255 << 24 | result << 16 | result << 8 | result;
        } else {
            red = getColorValue(red);
            green = getColorValue(green);
            blue = getColorValue(blue);
            return 255 << 24 | red << 16 | green << 8 | blue;
        }
    }

    public int getColorValue(int colorValue) {
        boolean border = colorValue > colorBinarization.get();
        int newColorValue;
        if (border) {
            if (sharpBorders.get()) {
                newColorValue = 255;
            } else {
                newColorValue = colorValue / divider;
            }
        } else {
            newColorValue = 0;
        }
        return newColorValue;
    }

    public IntegerProperty binarizationProperty() {
        return colorBinarization;
    }

    public BooleanProperty blackWhiteBordersProperty() {
        return blackWhiteBorders;
    }

    public BooleanProperty sharpBordersProperty() {
        return sharpBorders;
    }

}
