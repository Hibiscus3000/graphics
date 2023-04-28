package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ColorContainer implements Serializable {

    private final Map<String, Color> colors = new HashMap<>();

    public void putColor(String colorName, Color color) {
        colors.put(colorName, color);
    }

    public Color getColor(String colorName) {
        return colors.get(colorName);
    }
}
