package ru.nsu.fit.icg.lab2.filter.dithering.ordered;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;

import java.util.HashMap;
import java.util.Map;

public class OrderedDitheringFilter extends DitheringFilter {

    private static Integer[][] matrixForMatrixCreation;
    private static final Map<Integer, Integer[][]> matrices = new HashMap<>(); //matrixPreferredSide => matrix-0.5

    private final OrderedDitheringMatrix[] colorMatrices = new OrderedDitheringMatrix[Color.values().length];

    public OrderedDitheringFilter() {
        final int initialMatrixSide = 4;

        matrixForMatrixCreation = new Integer[2][2];
        matrixForMatrixCreation[0][0] = -2;
        matrixForMatrixCreation[0][1] = 0;
        matrixForMatrixCreation[1][0] = 1;
        matrixForMatrixCreation[1][1] = -1;
        matrices.put(2, matrixForMatrixCreation);
        matrixForMatrixCreation = getMatrix(initialMatrixSide);

        final int initialColorQuantization = 16;
        Color[] colors = Color.values();

        colorProperties = new OrdDitherQuantProperty[colors.length];
        for (Color color : colors) {
            int colorOrdinal = color.ordinal();
            colorMatrices[colorOrdinal] = new OrderedDitheringMatrix(initialMatrixSide,
                    initialColorQuantization);
            colorProperties[colorOrdinal] = new OrdDitherQuantProperty(colorMatrices[colorOrdinal]);
        }

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
        int redQuantization = colorProperties[redOrdinal].get();
        int greenQuantization = colorProperties[greenOrdinal].get();
        int blueQuantization = colorProperties[blueOrdinal].get();
        OrderedDitheringMatrix redMatrix = colorMatrices[redOrdinal];
        OrderedDitheringMatrix greenMatrix = colorMatrices[greenOrdinal];
        OrderedDitheringMatrix blueMatrix = colorMatrices[blueOrdinal];
        Integer[][] red = redMatrix.getMatrix();
        Integer[][] green = greenMatrix.getMatrix();
        Integer[][] blue = blueMatrix.getMatrix();
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
                            Integer[][] matrix, int divider, int matrixFactor) {
        int error = matrixFactor * matrix[y % matrixSide][x % matrixSide] / divider;
        return getNearestPaletteColor(oldColor + error, quantizationNumber);
    }

    public static Integer[][] getMatrix(int matrixSide) {
        if (null == matrices.get(matrixSide)) {
            int smallerMatrixSide = matrixSide / 2;
            matrixForMatrixCreation = getMatrix(smallerMatrixSide);

            Integer[][] matrix = new Integer[matrixSide][matrixSide];
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
    public OrdDitherQuantProperty getColorProperty(Color color) {
        return (OrdDitherQuantProperty) super.getColorProperty(color);
    }
}
