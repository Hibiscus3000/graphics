package ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline;

import javafx.util.Pair;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableSpline implements Serializable {

    private final int numberOfAnchorPoints;
    private final int splineSectorPartition;
    private final List<Pair<Double, Double>> anchorPoints = new ArrayList<>();

    public SerializableSpline(int numberOfAnchorPoints, int splineSectorPartition,
                              List<Point> anchorPoints) {
        this.numberOfAnchorPoints = numberOfAnchorPoints;
        this.splineSectorPartition = splineSectorPartition;
        for (Point anchorPoint : anchorPoints) {
            this.anchorPoints.add(new Pair<>(anchorPoint.uProperty().get(), anchorPoint.vProperty().get()));
        }
    }

    public int getNumberOfAnchorPoints() {
        return numberOfAnchorPoints;
    }

    public int getSplineSectorPartition() {
        return splineSectorPartition;
    }

    public List<Pair<Double, Double>> getAnchorPoints() {
        return anchorPoints;
    }
}
