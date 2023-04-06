package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;
import ru.nsu.fit.icg.lab2.filter.window.MedianFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.SharpeningFilter;

public class WaterColorizationFilter extends Filter {

    private final MedianFilter medianFilter = new MedianFilter();
    private final SharpeningFilter sharpeningFilter = new SharpeningFilter();

    @Override
    public WritableImage filter(WritableImage original) {
        return sharpeningFilter.filter(medianFilter.filter(original));
    }

    public MedianFilter getMedianFilter() {
        return medianFilter;
    }

    public SharpeningFilter getSharpeningFilter() {
        return sharpeningFilter;
    }

    @Override
    public String getName() {
        return "Акварелизация";
    }

    @Override
    public String getJsonName() {
        return "waterColorization";
    }
    //<a target="_blank" href="https://icons8.com/icon/P4attsDslSsf/palette">Palette</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
