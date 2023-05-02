package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ColorContainer implements Serializable {

    private final Map<String, ObjectProperty<Color>> colors = new HashMap<>();

    public ColorContainer() {
        putColor("background", Color.WHITE);
        putColor("mainAxis", Color.BLACK);
        putColor("secondaryAxis", Color.rgb(200, 200, 200));
        putColor("splineLine", Color.RED);
        putColor("generatrix", Color.BLUE);
        putColor("anchorPoint", Color.ORANGE);
        putColor("selectedAnchorPoint", Color.GREEN);
        putColor("splitPoint", Color.ORANGE);
        putColor("serif", Color.BLACK);
    }

    public ObjectProperty<Color> colorProperty(String colorName) {
        return colors.get(colorName);
    }

    public void putColor(String colorName, Color color) {
        if (colors.containsKey(colorName)) {
            colors.get(colorName).set(color);
        } else {
            colors.put(colorName, new SimpleObjectProperty<>(color));
        }
    }

    public Color getColor(String colorName) {
        return colors.get(colorName).get();
    }

    public Map<String, ObjectProperty<Color>> getColors() {
        return colors;
    }

    public void invokeAllListeners() {
        for (ObjectProperty<Color> colorProperty : colors.values()) {
            Color color = colorProperty.get();
            colorProperty.set(null);
            colorProperty.set(color);
        }
    }
}
