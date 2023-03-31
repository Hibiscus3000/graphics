package ru.nsu.fit.icg.lab2.filter.window.convolution;

import javafx.scene.image.PixelReader;
import ru.nsu.fit.icg.lab2.filter.window.WindowFilter;

public abstract class ConvolutionFilter extends WindowFilter {

    protected int[][] matrix;
    protected int divider = 1;
    protected int addition = 0;

    @Override
    protected int filterPixel(int x0, int y0, PixelReader pixelReader) {
        int red = addition, green = addition, blue = addition;
        for (int x = x0 - left; x <= x0 + right; ++x) {
            for (int y = y0 - left; y <= y0 + right; ++y) {
                int matrixX = x - x0 + left, matrixY = y - y0 + left;
                if (0 == matrix[matrixX][matrixY]) {
                    continue;
                }
                int color = pixelReader.getArgb(Math.max(0, Math.min(x, width - 1)),
                        Math.max(0, Math.min(y, height - 1)));
                int r = color >> 16 & 255, g = color >> 8 & 255, b = color & 255;
                red += matrix[matrixX][matrixY] * r;
                green += matrix[matrixX][matrixY] * g;
                blue += matrix[matrixX][matrixY] * b;
            }
        }
        return 255 << 24
                | Math.min(Math.max(0, red / divider), 255) << 16
                | Math.min(Math.max(0, green / divider), 255) << 8
                | Math.min(Math.max(0, blue / divider), 255);
    }

    @Override
    protected int getLeft() {
        return matrix.length >> 1;
    }

    @Override
    protected int getRight() {
        return (matrix.length >> 1) - (matrix.length & 1 ^ 1);
    }
}
