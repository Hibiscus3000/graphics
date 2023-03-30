package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.window.MedianFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.SharpeningFilter;

public class WaterColorizationFilter implements Filter {

    private final MedianFilter medianFilter = new MedianFilter();
    private final SharpeningFilter sharpeningFilter = new SharpeningFilter();

    @Override
    public WritableImage filter(WritableImage original) {
        return sharpeningFilter.filter(medianFilter.filter(original));
    }

    @Override
    public String getName() {
        return "Акварелизация";
    }

    @Override
    public String getJsonName() {
        return "waterColorization";
    }
}
