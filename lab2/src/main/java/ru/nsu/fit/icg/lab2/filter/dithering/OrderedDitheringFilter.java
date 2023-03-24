package ru.nsu.fit.icg.lab2.filter.dithering;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.Matrix;

import java.util.HashMap;
import java.util.Map;

public class OrderedDitheringFilter extends DitheringFilter {

    private final Map<Integer, int[][]> matrices = new HashMap<>(); //matrixPreferredSide => matrix-0.5

    private final OrderedDitheringMatrix[] colorMatrices = new OrderedDitheringMatrix[3];
    private int[][] matrixForMatrixCreation;

    public OrderedDitheringFilter() {
        matrixForMatrixCreation = new int[2][2];
        matrixForMatrixCreation[0][0] = -2;
        matrixForMatrixCreation[0][1] = 0;
        matrixForMatrixCreation[1][0] = 1;
        matrixForMatrixCreation[1][1] = -1;
        matrices.put(2, matrixForMatrixCreation);
        colorQuantization[0] = colorQuantization[1] = colorQuantization[2] = 16;
        final int initialSide = 4;
        matrixForMatrixCreation = getMatrix(initialSide);
        colorMatrices[0] = new OrderedDitheringMatrix(Color.RED, initialSide);
        colorMatrices[1] = new OrderedDitheringMatrix(Color.GREEN, initialSide);
        colorMatrices[2] = new OrderedDitheringMatrix(Color.BLUE, initialSide);
    }

    @Override
    public WritableImage filter(WritableImage original) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage filteredImage = new WritableImage(width, height);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        int redOrdinal = Color.RED.ordinal();
        int greenOrdinal = Color.GREEN.ordinal();
        int blueOrdinal = Color.BLUE.ordinal();
        int redQuantization = colorQuantization[redOrdinal];
        int greenQuantization = colorQuantization[greenOrdinal];
        int blueQuantization = colorQuantization[blueOrdinal];
        OrderedDitheringMatrix redMatrix = colorMatrices[redOrdinal];
        OrderedDitheringMatrix greenMatrix = colorMatrices[greenOrdinal];
        OrderedDitheringMatrix blueMatrix = colorMatrices[blueOrdinal];
        int[][] red = redMatrix.getMatrix();
        int[][] green = greenMatrix.getMatrix();
        int[][] blue = blueMatrix.getMatrix();
        int redMatrixSide = redMatrix.getSide();
        int greenMatrixSide = greenMatrix.getSide();
        int blueMatrixSide = blueMatrix.getSide();
        int redDivider = redMatrix.getSide() * redMatrix.getSide();
        int greenDivider = greenMatrix.getSide() * greenMatrix.getSide();
        int blueDivider = blueMatrix.getSide() * blueMatrix.getSide();
        int redMatrixFactor = 255 / (redQuantization - 1);
        int greenMatrixFactor = 255 / (greenQuantization - 1);
        int blueMatrixFactor = 255 / (blueQuantization - 1);

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int argb = pixelReader.getArgb(x, y);
                int newRed = filterPixel(x, y, argb >> 16 & 255, redQuantization,
                        redMatrixSide, red, redDivider, redMatrixFactor);
                int newGreen = filterPixel(x, y, argb >> 8 & 255, greenQuantization,
                        greenMatrixSide, green, greenDivider, greenMatrixFactor);
                int newBlue = filterPixel(x, y, argb & 255, blueQuantization,
                        blueMatrixSide, blue, blueDivider, blueMatrixFactor);
                pixelWriter.setArgb(x, y, 255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
            }
        }
        return filteredImage;
    }

    private int filterPixel(int x, int y, int oldColor, int quantizationNumber, int matrixSide,
                            int[][] matrix, int divider, int matrixFactor) {
        int error = matrixFactor * matrix[y % matrixSide][x % matrixSide] / divider;
        return getNearestPaletteColor(oldColor + error, quantizationNumber);
    }

    private int[][] getMatrix(int matrixSide) {
        if (null == matrices.get(matrixSide)) {
            int smallerMatrixSide = matrixSide / 2;
            matrixForMatrixCreation = getMatrix(smallerMatrixSide);

            int[][] matrix = new int[matrixSide][matrixSide];
            matrices.put(matrixSide, matrix);
            for (int i = 0; i < smallerMatrixSide; ++i) {
                for (int j = 0; j < smallerMatrixSide; ++j) {
                    matrix[i][j] = 4 * matrixForMatrixCreation[i][j];
                    matrix[i][j + smallerMatrixSide] = 4 * matrixForMatrixCreation[i][j] + 2;
                    matrix[i + smallerMatrixSide][j] = 4 * matrixForMatrixCreation[i][j] + 3;
                    matrix[i + smallerMatrixSide][j + smallerMatrixSide] = 4 * matrixForMatrixCreation[i][j] + 1;
                }
            }
        }

        return matrices.get(matrixSide);
    }

    public OrderedDitheringMatrix getMatrix(Color color) {
        return colorMatrices[color.ordinal()];
    }

    @Override
    public String getName() {
        return "Упорядоченный дизеринг";
    }

    @Override
    public String getJsonName() {
        return "orderedDithering";
    }

    @Override
    public boolean setQuantization(Color color, int quantization) {
        colorQuantization[color.ordinal()] = quantization;
        return colorMatrices[color.ordinal()].recalculateMatrix();
    }

    private class OrderedDitheringMatrix extends Matrix {
        private final Color color;
        private Integer matrixPreferredSide;
        private int matrixSide;

        OrderedDitheringMatrix(Color color, Integer matrixPreferredSide) {
            this.color = color;
            this.matrixPreferredSide = matrixPreferredSide;
            recalculateMatrix();
        }

        @Override
        public int[][] getMatrix() {
            return matrix;
        }

        @Override
        public boolean setPreferredSide(Integer matrixPreferredSide) {
            this.matrixPreferredSide = matrixPreferredSide;
            return recalculateMatrix();
        }

        @Override
        public Integer[] getSides() {
            return new Integer[]{2, 4, 8, 16, null};
        }

        @Override
        public Integer getSide() {
            return matrixSide;
        }

        private boolean recalculateMatrix() {
            int matrixSide;
            if (null == this.matrixPreferredSide) {
                matrixSide = getMatrixSideAuto();
            } else {
                matrixSide = this.matrixPreferredSide;
            }
            if (matrixSide != this.matrixSide) {
                matrix = OrderedDitheringFilter.this.getMatrix(matrixSide);
                this.matrixSide = matrixSide;
                return true;
            }
            return false;
        }

        private int getMatrixSideAuto() {
            int colorQuant = colorQuantization[color.ordinal()];
            if (colorQuant <= 4) {
                return 2;
            }
            if (colorQuant <= 16) {
                return 4;
            }
            if (colorQuant <= 64) {
                return 8;
            }
            return 16;
        }
    }
}
