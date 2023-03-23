package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.matrix.MatrixFilter;

public class FloydSteinbergFilter extends MatrixFilter {

    private int redQuantization = 8;
    private int greenQuantization = 8;
    private int blueQuantization = 8;

    @Override
    public String getName() {
        return "Дизеринг Флойда-Стейнберга";
    }

    @Override
    public String getJsonName() {
        return "floydSteinberg";
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
        int[][] r = new int[width][height];
        int[][] g = new int[width][height];
        int[][] b = new int[width][height];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int argb = pixelReader.getArgb(x, y);
                int newRed = filterPixel(x, y, argb >> 16 & 255, redQuantization, r);
                int newGreen = filterPixel(x, y, argb >> 8 & 255, greenQuantization, g);
                int newBlue = filterPixel(x, y, argb & 255, blueQuantization, b);
                pixelWriter.setArgb(x, y, 255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
            }
        }
        return filteredImage;
    }

    private int filterPixel(int x, int y, int color, int quantizationNumber, int[][] errors) {
        int newColor = getNearestPaletteColor(color = (color + errors[x][y]) & 255,
                quantizationNumber);
        int error = color - newColor;
        if (x != width - 1 && y != height - 1) {
            errors[x + 1][y] += 7 * error / 16;
            if (0 != x) {
                errors[x - 1][y + 1] += 3 * error / 16;
            }
            errors[x][y + 1] += 5 * error / 16;
            errors[x + 1][y + 1] += error / 16;
        }
        if (x == width - 1 && y != height - 1) {
            errors[x - 1][y + 1] += 3 * error / 8;
            errors[x][y + 1] += 5 * error / 8;
        }
        if (x != width - 1 && y == height - 1) {
            errors[x + 1][y] += error;
        }
        return newColor;
    }

    private int getNearestPaletteColor(int color, int quantizationNumber) {
        final int colorDistance = 255 / (quantizationNumber - 1);
        int colorQuanta = color / colorDistance;
        final int lesser = colorDistance * colorQuanta;
        final int bigger = Math.min(colorDistance * quantizationNumber,
                colorDistance * (colorQuanta + 1));
        return color - lesser > bigger - color ? bigger : lesser;
    }

    public void setRedQuantization(int redQuantization) {
        this.redQuantization = redQuantization;
    }

    public void setGreenQuantization(int greenQuantization) {
        this.greenQuantization = greenQuantization;
    }

    public void setBlueQuantization(int blueQuantization) {
        this.blueQuantization = blueQuantization;
    }

    public int getRedQuantization() {
        return redQuantization;
    }

    public int getGreenQuantization() {
        return greenQuantization;
    }

    public int getBlueQuantization() {
        return blueQuantization;
    }
}
