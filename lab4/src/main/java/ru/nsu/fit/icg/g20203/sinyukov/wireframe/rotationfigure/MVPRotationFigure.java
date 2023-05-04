package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure;

import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point2D;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point3D;

import java.util.ArrayList;
import java.util.List;

public class MVPRotationFigure {

    private final List<List<Point2D>> lines = new ArrayList<>();

    public MVPRotationFigure(MVP mvp, RotationFigure rotationFigure) {
        for (List<Point3D> line : rotationFigure.getLines()) {
            List<Point2D> newLine = new ArrayList<>();
            for (Point3D point : line) {
                newLine.add(new Point2D(mvp.calcPoint(point.get4DVector())));
            }
            lines.add(newLine);
        }
    }

    public List<List<Point2D>> getLines() {
        return lines;
    }
}
