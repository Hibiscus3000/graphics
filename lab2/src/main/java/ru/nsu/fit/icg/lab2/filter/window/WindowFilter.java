package ru.nsu.fit.icg.lab2.filter.window;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class WindowFilter extends Filter {

    protected int width, height;
    protected int left, right;

    @Override
    public final WritableImage filter(WritableImage original) {
        width = (int) original.getWidth();
        height = (int) original.getHeight();
        left = getLeft();
        right = getRight();
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

    protected abstract int filterPixel(int x0, int y0, PixelReader pixelReader);

    protected abstract int getLeft();

    protected abstract int getRight();
}
