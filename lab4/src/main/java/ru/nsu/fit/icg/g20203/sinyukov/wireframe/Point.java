package ru.nsu.fit.icg.g20203.sinyukov.wireframe;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Pair;

public class Point {

    private transient SimpleDoubleProperty u;
    private transient SimpleDoubleProperty v;

    public Point(double u, double v) {
        this.u = new SimpleDoubleProperty(u);
        this.v = new SimpleDoubleProperty(v);
    }

    public Point(Pair<Double, Double> coordinates) {
        this(coordinates.getKey(), coordinates.getValue());
    }

    public DoubleProperty uProperty() {
        return u;
    }

    public DoubleProperty vProperty() {
        return v;
    }
}
