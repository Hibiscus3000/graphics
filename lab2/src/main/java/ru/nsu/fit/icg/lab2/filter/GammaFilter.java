package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import static java.lang.Math.pow;

public class GammaFilter implements Filter {

    private double gamma = 1;

    @Override
    public String getName() {
        return "Гамма-коррекция";
    }

    @Override
    public String getJsonName() {
        return "gamma";
    }

    @Override
    public WritableImage filter(WritableImage original) {
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        WritableImage filteredImage = new WritableImage(width, height);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        System.out.println(gamma);
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int argb = pixelReader.getArgb(x, y);
                int r = Math.min((int) pow(argb >> 16 & 255, gamma), 255);
                int g = Math.min((int) pow(argb >> 8 & 255, gamma), 255);
                int b = Math.min((int) pow(argb & 255, gamma), 255);
                pixelWriter.setArgb(x, y, 255 << 24 | r << 16 | g << 8 | b);
            }
        }
        return filteredImage;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }
}
