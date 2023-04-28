package ru.nsu.fit.icg.g20203.sinyukov.wireframe.generatrix;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.icg.g20203.sinyukov.wireframe.Math.*;

public class Generatrix implements Serializable {

    // number of lines that form one B-spline segment
    private final IntegerProperty numberOfSplineLines;
    private final transient List<List<Point>> splineLines = new ArrayList<>();
    private final List<Point> anchorPoints = new ArrayList<>();

    public Generatrix(int numberOfAnchorPoints, int numberOfLines) {
        this.numberOfSplineLines = new SimpleIntegerProperty(numberOfLines);
        addAnchorPoints(numberOfAnchorPoints, -1);
    }

    public List<Point> addAnchorPoints(int numberOfAnchorPoints, int lastAPIndex) {
        List<Point> newAnchorPoints = new ArrayList<>();
        for (int i = 0; i < numberOfAnchorPoints; ++i) {
            newAnchorPoints.add(addAnchorPoint(lastAPIndex + i));
        }
        return newAnchorPoints;
    }

    private final static double distFromPreviousAP = 20.0;

    public Point addAnchorPoint(int lastAPIndex) {
        Point newAP;
        if (lastAPIndex >= 1) {
            Point preLastAP = anchorPoints.get(lastAPIndex);
            Point lastAP = anchorPoints.get(lastAPIndex - 1);
            double lastAPU = lastAP.uProperty().get();
            double lastAPV = preLastAP.uProperty().get();
            double du = lastAPU - lastAPV;
            double dv = lastAP.vProperty().get() - preLastAP.vProperty().get();
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
        anchorPoints.add(newAP);
        calculateAP(lastAPIndex + 1);
        return newAP;
    }

    private void calculateAP(int APIndex) {
        for (int j = APIndex - 1; j < APIndex + 3; ++j) {
            calculateLine(j);
        }
    }

    // AP[i] <=> SplineLine[i - 1]
    private void calculateLine(int APIndex) {
        if (0 <= APIndex || APIndex > anchorPoints.size() - 3) {
            return;
        }
        if (splineLines.size() < anchorPoints.size() - 3) {
            List<Point> newSplineLine = new ArrayList<>();
            splineLines.add(APIndex - 1, newSplineLine);
        }
        List<Point> calculatedSplineLine = splineLines.get(APIndex - 1);
        int numberOfPoints = calculatedSplineLine.size();
        for (int i = 0; i <= numberOfSplineLines.get(); ++i) {
            double[] uv = calculateSLPoint(i, APIndex);
            if (i < numberOfPoints) {
                Point calculatedPoint = calculatedSplineLine.get(i);
                calculatedPoint.uProperty().set(uv[0]);
                calculatedPoint.vProperty().set(uv[1]);
            } else {
                calculatedSplineLine.add(new Point(uv[0], uv[1]));
            }
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
        for (int j = 3; j >= 0; --j) {
            T[j] = Math.pow((double) pointInd / numberOfSplineLines.get(), j);
        }
        return lineMatrixProduct(T, scalarMatrixProduct(1.0 / 6,
                matrixProduct(Ms, P, 4, 4, 2), 4, 2), 4, 2);
    }
}
