package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.List;

public class ShapeColorListener<T extends Shape> implements ChangeListener<Color> {

    private final List<T> shapes;

    public ShapeColorListener(List<T> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void changed(ObservableValue<? extends Color> observableValue, Color oldColor, Color newColor) {
        for (Shape shape : shapes) {
            shape.setStroke(newColor);
            shape.setFill(newColor);
            shape.setStroke(newColor);
        }
    }
}
