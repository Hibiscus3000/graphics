package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;

public class GammaFilter implements Filter {

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
        return null;
    }
}
