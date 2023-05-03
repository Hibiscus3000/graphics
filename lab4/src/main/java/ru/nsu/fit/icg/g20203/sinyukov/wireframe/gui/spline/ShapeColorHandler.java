package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.List;
import java.util.function.Consumer;

public class ShapeColorHandler<T extends Shape> implements Consumer<Color> {

    private final List<T> shapes;

    public ShapeColorHandler(List<T> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void accept(Color color) {
        for (Shape shape : shapes) {
            shape.setStroke(color);
            shape.setFill(color);
            shape.setStroke(color);
        }
    }
}
