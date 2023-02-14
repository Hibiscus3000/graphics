package ru.nsu.fit.graphics.exercise1;

public class Line {

    private final double startXScale;
    private final double startYScale;
    private final double endXScale;
    private final double endYScale;

    public Line(double startXScale, double startY, double endX, double endYScale) {
        this.startXScale = startXScale;
        this.startYScale = startY;
        this.endXScale = endX;
        this.endYScale = endYScale;
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
}
