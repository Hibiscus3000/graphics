package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure;

import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Point;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point3D;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

import java.util.ArrayList;
import java.util.List;

public class RotationFigure {

    private final List<List<Point3D>> lines = new ArrayList<>();

    public RotationFigure(int numberOfGenerators, int numberOfLinesBetweenGenerators,
                          Spline spline) {
        List<List<Point>> splineSectors = spline.getSplineSectors();
        createGenerators(numberOfGenerators, splineSectors);
        createCircles(numberOfGenerators, numberOfLinesBetweenGenerators, splineSectors);
    }

    private void createGenerators(int numberOfGenerators, List<List<Point>> splineSectors) {
        for (int i = 0; i < numberOfGenerators; ++i) {
            double ang = 2 * Math.PI * i / numberOfGenerators;
            for (List<Point> splineSector : splineSectors) {
                List<Point3D> newLine = new ArrayList<>();
                for (Point splineLinePoint : splineSector) {
                    newLine.add(createPoint3D(ang, splineLinePoint));
                }
                lines.add(newLine);
            }
        }
    }

    private void createCircles(int numberOfGenerators, int numberOfLinesBetweenGenerators, List<List<Point>> splineSectors) {
        for (List<Point> splineSector : splineSectors) {
            createCircle(numberOfGenerators, numberOfLinesBetweenGenerators, splineSector.get(0));
        }
        List<Point> lastSplineSector = splineSectors.get(splineSectors.size() - 1);
        createCircle(numberOfGenerators, numberOfLinesBetweenGenerators, lastSplineSector.get(lastSplineSector.size() - 1));
    }

    private void createCircle(int numberOfGenerators, int numberOfLinesBetweenGenerators, Point splinePoint) {
        for (int i = 0; i < numberOfGenerators; ++i) {
            List<Point3D> newLine = new ArrayList<>();
            for (int j = 0; j < numberOfLinesBetweenGenerators; ++j) {
                newLine.add(createPoint3D(i * j * 2 * Math.PI / numberOfGenerators / numberOfLinesBetweenGenerators, splinePoint));
            }
        }
    }

    private Point3D createPoint3D(double ang, Point point) {
        double u = point.getU();
        double v = point.getV();
        return new Point3D(v * Math.cos(ang), v * Math.sin(ang), u);
    }

    private void normalizeToFixBox() {
        double max = 0;
        for (List<Point3D> line : lines) {
            for (Point3D point : line) {
                max = Math.max(max, Math.abs(point.getX()));
                max = Math.max(max, Math.abs(point.getY()));
                max = Math.max(max, Math.abs(point.getY()));
            }
        }
        for (List<Point3D> line : lines) {
            for (Point3D point : line) {
                point.divide(max);
            }
        }
    }

    public List<List<Point3D>> getLines() {
        return lines;
    }
}
