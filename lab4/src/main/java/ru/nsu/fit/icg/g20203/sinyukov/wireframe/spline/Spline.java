package ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.icg.g20203.sinyukov.wireframe.Math.*;

public class Spline implements Serializable {

    private final IntegerProperty numberOfAnchorPoints;
    // number of lines that form one B-spline segment
    private final IntegerProperty splineSectorPartition;
    private final transient List<List<Point>> splineLines = new ArrayList<>();
    private final List<Point> anchorPoints = new ArrayList<>();

    public Spline(int numberOfAnchorPoints, int numberOfLines) {
        this.numberOfAnchorPoints = new SimpleIntegerProperty(numberOfAnchorPoints);
        this.splineSectorPartition = new SimpleIntegerProperty(numberOfLines);
        addAnchorPointsToEnd(numberOfAnchorPoints);
    }

    public void addAnchorPointsToEnd(int numberOfAnchorPoints) {
        addAnchorPoints(numberOfAnchorPoints, anchorPoints.size() - 1);
    }

    public void addAnchorPoints(int numberOfAnchorPoints, int lastAPIndex) {
        for (int i = 0; i < numberOfAnchorPoints; ++i) {
            addAnchorPoint(lastAPIndex + i);
        }
    }

    public void removeAnchorPointsFromEnd(int numberOfAnchorPoints) {
        removeAnchorPoints(numberOfAnchorPoints, anchorPoints.size() - 1);
    }

    public void removeAnchorPoints(int numberOfAnchorPoints, int lastAPIndex) {
        for (int i = 0; i < numberOfAnchorPoints; ++i) {
            removeAnchorPoint(lastAPIndex - i);
        }
    }

    private final static double distFromPreviousAP = 20.0;

    public void addAnchorPoint(int lastAPIndex) {
        Point newAP;
        if (lastAPIndex >= 1) {
            Point preLastAP = anchorPoints.get(lastAPIndex - 1);
            Point lastAP = anchorPoints.get(lastAPIndex);
            double lastAPU = lastAP.uProperty().get();
            double lastAPV = lastAP.vProperty().get();
            double du = lastAPU - preLastAP.uProperty().get();
            double dv = lastAPV - preLastAP.vProperty().get();
            double angle = getAngle(du, dv);
            newAP = new Point(lastAPU + Math.cos(angle) * distFromPreviousAP,
                    lastAPV + Math.sin(angle) * distFromPreviousAP);
        } else {
            if (0 == lastAPIndex) {
                newAP = new Point(anchorPoints.get(0).uProperty().get() + distFromPreviousAP,
                        anchorPoints.get(0).vProperty().get());
            } else {
                newAP = new Point(0, 0);
            }
        }
        int apIndex = lastAPIndex + 1;
        anchorPoints.add(apIndex,newAP);
        calculateAP(apIndex);
        calculateAP(lastAPIndex);
        ChangeListener<Number> apChangeListener =
                (observableValue, oldValue, newValue) -> calculateAP(apIndex);
        newAP.uProperty().addListener(apChangeListener);
        newAP.vProperty().addListener(apChangeListener);
    }

    public void removeAnchorPoint(int APIndex) {
        anchorPoints.remove(APIndex);
        int splineLineInd =  Math.max(0,Math.min(APIndex - 1,splineLines.size() - 1));
        if (anchorPoints.size() >= 3) {
            splineLines.remove(splineLineInd);
        }
        if (anchorPoints.size() >= 4) {
            calculateLine(splineLineInd);
        }
    }

    private void calculateAP(int APIndex) {
        calculateAllLines();
        //TODO
//        for (int j = APIndex - 5; j <= APIndex + 5; ++j) {
//            calculateLine(j);
//        }
    }

    // AP[i] <=> SplineLine[i - 1]
    private void calculateLine(int APIndex) {
        if (0 >= APIndex || APIndex > anchorPoints.size() - 3) {
            return;
        }
        while (splineLines.size() < anchorPoints.size() - 3) {
            List<Point> newSplineLine = new ArrayList<>();
            splineLines.add(APIndex - 1, newSplineLine);
        }
        List<Point> calculatedSplineLine = splineLines.get(APIndex - 1);
        int numberOfPoints = calculatedSplineLine.size();
        for (int i = 0; i <= splineSectorPartition.get(); ++i) {
            double[] uv = calculateSLPoint(i, APIndex);
            if (i < numberOfPoints) {
                Point calculatedPoint = calculatedSplineLine.get(i);
                calculatedPoint.uProperty().set(uv[0]);
                calculatedPoint.vProperty().set(uv[1]);
            } else {
                calculatedSplineLine.add(new Point(uv[0], uv[1]));
            }
        }
        int removeFrom = splineSectorPartition.get() + 1;
        for (int i = removeFrom; i < calculatedSplineLine.size(); ++i) {
            calculatedSplineLine.remove(removeFrom);
        }
    }

    public void calculateAllLines() {
        for (int j = 1; j < anchorPoints.size(); ++j) {
            calculateLine(j);
        }
    }

    private final static double[][] Ms = new double[][]{
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 0, 3, 0},
            {1, 4, 1, 0}};

    // returns [u,v]
    private double[] calculateSLPoint(int pointInd, int APInd) {
        double[][] P = new double[4][2];
        for (int j = 0; j < 4; ++j) {
            Point anchorPoint = anchorPoints.get(j + APInd - 1);
            P[j][0] = anchorPoint.uProperty().get();
            P[j][1] = anchorPoint.vProperty().get();
        }
        double[] T = new double[4];
        for (int j = 0; j < 4; ++j) {
            T[3 - j] = Math.pow((double) pointInd / splineSectorPartition.get(), j);
        }
        return lineMatrixProduct(T, scalarMatrixProduct(1.0 / 6,
                matrixProduct(Ms, P, 4, 4, 2), 4, 2), 4, 2);
    }

    public IntegerProperty splineSectorPartitionProperty() {
        return splineSectorPartition;
    }

    public IntegerProperty numberOfAnchorPointsProperty() {
        return numberOfAnchorPoints;
    }

    public List<List<Point>> getSplineLines() {
        return splineLines;
    }

    public List<Point> getAnchorPoints() {
        return anchorPoints;
    }
}
