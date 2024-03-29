package ru.nsu.fit.graphics.exercise1;

import java.awt.*;

public class Line {

    private final double startXScale;
    private final double startYScale;
    private final double endXScale;
    private final double endYScale;

    private float width;
    private Color color;

    public Line(double startXScale, double startY, double endX, double endYScale, float width, Color color) {
        this.startXScale = startXScale;
        this.startYScale = startY;
        this.endXScale = endX;
        this.endYScale = endYScale;

        this.width = width;
        this.color = color;
    }

    public double getStartXScale() {
        return startXScale;
    }

    public double getStartYScale() {
        return startYScale;
    }

    public double getEndXScale() {
        return endXScale;
    }

    public double getEndYScale() {
        return endYScale;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
