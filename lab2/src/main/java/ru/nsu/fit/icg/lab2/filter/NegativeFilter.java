package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;

public class NegativeFilter implements Filter {

    @Override
    public String getName() {
        return "Негатив";
    }

    @Override
    public String getJsonName() {
        return "negative";
    }

    @Override
    public WritableImage filter(WritableImage original) {
        return null;
    }
}
