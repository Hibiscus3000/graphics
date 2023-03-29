package ru.nsu.fit.icg.lab2.filter.convolution;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class ConvolutionFilter implements Filter {

    protected Integer[][] matrix;
    protected int divider;

    @Override
    public final WritableImage filter(WritableImage original) {
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

    private int width;
    private int height;

    private int filterPixel(int x0, int y0, PixelReader pixelReader) {
        int red = 0, green = 0, blue = 0;
        int left = matrix.length >> 1, right = (matrix.length >> 1) - (matrix.length & 1 ^ 1);
        for (int x = x0 - left; x <= x0 + right; ++x) {
            for (int y = y0 - left; y <= y0 + right; ++y) {
                int matrixX = x - x0 + left, matrixY = y - y0 + left;
                if (null == matrix[matrixX][matrixY]) {
                    continue;
                }
                int color = pixelReader.getArgb(Math.max(0, Math.min(x, width - 1)),
                        Math.max(0, Math.min(y, height - 1)));
                int r = color >> 16 & 255, g = color >> 8 & 255, b = color & 255;
                red += matrix[matrixX][matrixY] * r / divider;
                green += matrix[matrixX][matrixY] * g / divider;
                blue += matrix[matrixX][matrixY] * b / divider;
            }
        }
        return 255 << 24 | red << 16 | green << 8 | blue;
    }
}
