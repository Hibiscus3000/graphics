package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RoundBlurFilter implements Filter {

    @Override
    public WritableImage filter(WritableImage original) {
        final int width = (int) original.getWidth();
        final int height = (int) original.getHeight();
        final int windowSize = 21;
        WritableImage filteredImage = new WritableImage(width, height);
        PixelReader pixelReader = original.getPixelReader();
        PixelWriter pixelWriter = filteredImage.getPixelWriter();
        int halfWidth = width / 2, halfHeight = height / 2;
        final int stepToMakeDegree = 2;
        double step = Math.PI / (180 * stepToMakeDegree);
        for (int x0 = 0; x0 < width; ++x0) {
            for (int y0 = 0; y0 < height; ++y0) {
                int xShift = (x0 - halfWidth);
                int yShift = (y0 - halfHeight);
                double r = Math.sqrt(xShift * xShift + yShift * yShift);
                int red = 0, green = 0, blue = 0;
                int x = x0, y = y0;
                int numberOfPixelsIncluded = 0;
                double angle, initialAngle;
                if (0 == xShift) {
                    if (yShift > 0) {
                        initialAngle = Math.PI / 2;
                    } else {
                        initialAngle = -Math.PI / 2;
                    }
                } else {
                    initialAngle = Math.atan((double) yShift / xShift);
                    if (xShift < 0) {
                        if (yShift > 0) {
                            initialAngle += Math.PI;
                        } else {
                            initialAngle -= Math.PI;
                        }
                    }
                }
                angle = initialAngle;
                for (int i = 0; i < windowSize; ++i) {
                    xShift = (int) (r * Math.cos(angle));
                    yShift = (int) (r * Math.sin(angle));
                    x = xShift + halfWidth;
                    y = yShift + halfHeight;
                    if (x >= 0 && x <= width - 1 && y >= 0 && y <= height - 1) {
                        int argb = pixelReader.getArgb(x, y);
                        red += argb >> 16 & 255;
                        green += argb >> 8 & 255;
                        blue += argb & 255;
                        ++numberOfPixelsIncluded;
                    }
                    angle += step;
                    if (i == windowSize / 2 + 1) {
                        step *= -1;
                        angle = initialAngle + step;
                    }
                }
                step *= -1;
                pixelWriter.setArgb(x0, y0,
                        255 << 24
                                | (red / numberOfPixelsIncluded) << 16
                                | (green / numberOfPixelsIncluded) << 8
                                | (blue / numberOfPixelsIncluded));
            }
        }
        return filteredImage;
    }

    @Override
    public String getName() {
        return "Радиальное размытие";
    }

    @Override
    public String getJsonName() {
        return "roundBlur";
    }
}
