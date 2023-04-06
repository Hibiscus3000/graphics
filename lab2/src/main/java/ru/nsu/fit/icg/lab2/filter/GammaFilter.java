package ru.nsu.fit.icg.lab2.filter;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import static java.lang.Math.pow;

public class GammaFilter extends Filter {

    private final SimpleDoubleProperty gamma = new SimpleDoubleProperty(1);

    @Override
    public WritableImage filter(WritableImage original) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        double gamma = this.gamma.get();
        WritableImage filteredImage = new WritableImage(width, height);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int argb = pixelReader.getArgb(x, y);
                int r = argb >> 16 & 255;
                int g = argb >> 8 & 255;
                int b = argb & 255;
                r = (int) Math.min(pow((double) r / 255, 1 / gamma) * 255, 255);
                g = (int) Math.min(pow((double) g / 255, 1 / gamma) * 255, 255);
                b = (int) Math.min(pow((double) b / 255, 1 / gamma) * 255, 255);
                pixelWriter.setArgb(x, y, 255 << 24 | r << 16 | g << 8 | b);
            }
        }
        return filteredImage;
    }

    public SimpleDoubleProperty gammaProperty() {
        return gamma;
    }

    @Override
    public String getName() {
        return "Гамма-коррекция";
    }

    @Override
    public String getJsonName() {
        return "gamma";
    }
    //<a target="_blank" href="https://icons8.com/icon/82718/sun">Sun</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
