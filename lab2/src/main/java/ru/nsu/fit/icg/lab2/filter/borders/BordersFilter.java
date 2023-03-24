package ru.nsu.fit.icg.lab2.filter.borders;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class BordersFilter implements Filter {

    private final int[][] first;
    private final int[][] second;
    private final int divider;
    private int[] binarization;

    protected BordersFilter(int[][] first, int[][] second, int divider, int[] binarization) {
        this.first = first;
        this.second = second;
        this.binarization = binarization;
        this.divider = divider;
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
        int redBinarization = binarization[Color.RED.ordinal()];
        int greenBinarization = binarization[Color.GREEN.ordinal()];
        int blueBinarization = binarization[Color.BLUE.ordinal()];
        int red = Math.abs(redFirst) + Math.abs(redSecond);
        int green = Math.abs(greenFirst) + Math.abs(greenSecond);
        int blue = Math.abs(blueFirst) + Math.abs(blueSecond);
        red = red > redBinarization ? red / divider : 0;
        green = green > greenBinarization ? green / divider : 0;
        blue = blue > blueBinarization ? blue / divider : 0;
        return 255 << 24 | red << 16 | green << 8 | blue;
    }

    public int getBinarization(Color color) {
        return binarization[color.ordinal()];
    }

    public void setBinarization(Color color, int binarization) {
        this.binarization[color.ordinal()] = binarization;
    }
}
