package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;

public interface Filter {

    WritableImage filter(WritableImage original);

    String getName();

    String getJsonName();
}
