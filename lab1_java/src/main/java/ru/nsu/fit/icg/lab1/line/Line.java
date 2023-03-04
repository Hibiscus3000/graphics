package ru.nsu.fit.icg.lab1.line;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private final List<Point> points = new ArrayList<>();

    public Line(Point startPoint) {
        points.add(startPoint);
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void setSecondPoint(Point point) {
        if (2 <= points.size()) {
            points.remove(1);
        }
        points.add(point);
    }

    public List<Point> getPoints() {
        return points;
    }
}
