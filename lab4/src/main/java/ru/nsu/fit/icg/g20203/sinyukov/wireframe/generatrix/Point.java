package ru.nsu.fit.icg.g20203.sinyukov.wireframe.generatrix;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Point {

    private final SimpleDoubleProperty u;
    private final SimpleDoubleProperty v;

    public Point(double u, double v) {
        this.u = new SimpleDoubleProperty(u);
        this.v = new SimpleDoubleProperty(v);
    }

    public DoubleProperty uProperty() {
        return u;
    }

    public DoubleProperty vProperty() {
        return v;
    }
}
