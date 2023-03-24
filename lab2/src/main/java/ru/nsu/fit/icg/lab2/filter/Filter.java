package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;

public interface Filter {

    enum Color {
        RED, GREEN, BLUE
    }

    String getName();

    String getJsonName();

    WritableImage filter(WritableImage original);
}
