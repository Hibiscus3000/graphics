package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class InversionFilter extends Filter {

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
                int r = argb >> 16 & 255;
                int g = argb >> 8 & 255;
                int b = argb & 255;
                pixelWriter.setArgb(x, y, 255 << 24 | (255 - r) << 16 | (255 - g) << 8 | (255 - b));
            }
        }
        return filteredImage;
    }

    @Override
    public String getName() {
        return "Инверсия";
    }

    @Override
    public String getJsonName() {
        return "inversion";
    }
    //<a target="_blank" href="https://icons8.com/icon/95855/negative">Negative</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
