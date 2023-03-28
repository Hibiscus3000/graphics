package ru.nsu.fit.icg.lab2.filter.dithering;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class FloydSteinbergFilter extends DitheringFilter {

    public FloydSteinbergFilter() {
        Color[] colors = Color.values();
        colorProperties = new SimpleIntegerProperty[colors.length];
        for (Color color : colors) {
            colorProperties[color.ordinal()] = new SimpleIntegerProperty(8);
        }
    }

    @Override
    public String getName() {
        return "Дизеринг Флойда-Стейнберга";
    }

    @Override
    public String getJsonName() {
        return "floydSteinberg";
    }

    private int imageWidth;
    private int imageHeight;

    @Override
    public WritableImage filter(WritableImage original) {
        imageWidth = (int) original.getWidth();
        imageHeight = (int) original.getHeight();
        WritableImage filteredImage = new WritableImage(imageWidth, imageHeight);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        int[][] r = new int[imageWidth][imageHeight];
        int[][] g = new int[imageWidth][imageHeight];
        int[][] b = new int[imageWidth][imageHeight];
        for (int x = 0; x < imageWidth; ++x) {
            for (int y = 0; y < imageHeight; ++y) {
                int argb = pixelReader.getArgb(x, y);
                int newRed = filterPixel(x, y, argb >> 16 & 255,
                        colorProperties[Color.RED.ordinal()].get(), r);
                int newGreen = filterPixel(x, y, argb >> 8 & 255,
                        colorProperties[Color.GREEN.ordinal()].get(), g);
                int newBlue = filterPixel(x, y, argb & 255,
                        colorProperties[Color.BLUE.ordinal()].get(), b);
                pixelWriter.setArgb(x, y, 255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
            }
        }
        return filteredImage;
    }

    private int filterPixel(int x, int y, int color, int quantizationNumber, int[][] errors) {
        int newColor = getNearestPaletteColor(color = color + errors[x][y],
                quantizationNumber);
        int error = color - newColor;
        if (x != imageWidth - 1 && y != imageHeight - 1) {
            errors[x + 1][y] += 7 * error / 16;
            if (0 != x) {
                errors[x - 1][y + 1] += 3 * error / 16;
            }
            errors[x][y + 1] += 5 * error / 16;
            errors[x + 1][y + 1] += error / 16;
        }
        if (x == imageWidth - 1 && y != imageHeight - 1) {
            errors[x - 1][y + 1] += 3 * error / 8;
            errors[x][y + 1] += 5 * error / 8;
        }
        if (x != imageWidth - 1 && y == imageHeight - 1) {
            errors[x + 1][y] += error;
        }
        return newColor;
    }

}
