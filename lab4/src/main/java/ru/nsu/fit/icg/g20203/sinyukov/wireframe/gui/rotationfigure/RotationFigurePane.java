package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.MVPRotationFigure;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point2D;

import java.util.ArrayList;
import java.util.List;

public class RotationFigurePane extends Pane {

    private final static double strokeWidth = 1;
    private final List<Line> lines = new ArrayList<>();

    public void setMVPRotationFigure(MVPRotationFigure mvpRotationFigure, Color color) {
        for (List<Point2D> line : mvpRotationFigure.getLines()) {
            for (int i = 1; i < line.size(); ++i) {
                Point2D previousPoint = line.get(i - 1);
                Point2D currentPoint = line.get(i);
                Line l = new Line(previousPoint.getU(), previousPoint.getV(),
                        currentPoint.getU(), currentPoint.getV());
                l.setStrokeWidth(strokeWidth);
                lines.add(l);
                getChildren().add(l);
            }
        }
        setColor(color);
    }

    public void setColor(Color color) {
        for (Line line : lines) {
            line.setFill(color);
            line.setStroke(color);
        }
    }
}
