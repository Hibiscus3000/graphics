package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class BlackWhiteFilter extends Filter {

    @Override
    public String getName() {
        return "Черно-белый фильтр";
    }

    @Override
    public String getJsonName() {
        return "blackWhite";
    }

    @Override
    public WritableImage filter(WritableImage original) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage filteredImage = new WritableImage(width, height);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int argb = pixelReader.getArgb(x, y);
                pixelWriter.setArgb(x, y, getBlackWhiteArgb(argb));
            }
        }
        return filteredImage;
    }

    private int getBlackWhiteArgb(int argb) {
        int r = argb >> 16 & 255;
        int g = argb >> 8 & 255;
        int b = argb & 255;
        int result = getBlackWhite(r, g, b);
        return 255 << 24 | result << 16 | result << 8 | result;
    }

    public static int getBlackWhite(int red, int green, int blue) {
        return Math.min((int) (0.299 * red + 0.587 * green + 0.144 * blue), 255);
    }
}
