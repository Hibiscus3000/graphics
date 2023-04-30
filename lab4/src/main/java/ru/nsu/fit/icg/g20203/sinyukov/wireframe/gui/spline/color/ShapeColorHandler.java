package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.List;

public class ShapeColorHandler<T extends Shape> extends ColorHandler {

    public ShapeColorHandler(String name, Color defaultColor, ColorContainer colorContainer, List<T> shapes) {
        super(name, defaultColor, colorContainer, color -> {
            for (Shape shape : shapes) {
                shape.setStroke(color);
                shape.setFill(color);
                shape.setStroke(color);
            }
        });
    }


}
