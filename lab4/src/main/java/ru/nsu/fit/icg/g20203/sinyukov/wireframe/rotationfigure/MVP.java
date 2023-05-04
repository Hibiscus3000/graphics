package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Math;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point3D;

public class MVP {

    private double M[][] = new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}};

    private final Point3D lookAt = new Point3D(10, 0, 0);
    private final Point3D eye = new Point3D(-10, 0, 0);
    private final Point3D up = new Point3D(0, 0, 1);

    // (e1, e2, e3) - basis vector in eye coordinate system
    private final Vector e1;
    private final Vector e2;
    private final Vector e3;

    private final double[][] V;

    // near and far clipping panes
    private final DoubleProperty N = new SimpleDoubleProperty(0.1);
    private final double F = 1;

    private double[][] P;
    private double[][] MVP;

    public MVP() {
        e3 = lookAt.subtract(eye).getVector();
        e2 = e3.vectorProduct(up.getVector());
        e1 = e3.vectorProduct(e2);

        Vector eyeVector = eye.getVector();
        double x0 = -e1.scalarProduct(eyeVector);
        double y0 = -e2.scalarProduct(eyeVector);
        double z0 = -e3.scalarProduct(eyeVector);
        V = new double[][]{
                {e1.getX(), e1.getY(), e1.getZ(), x0},
                {e2.getX(), e2.getY(), e3.getZ(), y0},
                {e3.getX(), e3.getY(), e3.getZ(), z0},
                {0, 0, 0, 1}};
        updateP();
        N.addListener((observableValue, oldVal, newVal) -> updateP());
    }

    private void updateP() {
        P = new double[][]{
                {N.get(), 0, 0, 0},
                {0, N.get(), 0, 0},
                {0, 0, getA(), getB()},
                {0, 0, 1, 0}};
        updateMVP();
    }

    private void updateMVP() {
        MVP = Math.matrixProduct(Math.matrixProduct(P, V, 4, 4, 4), M, 4, 4, 4);
    }

    private double getA() {
        return F / (F - N.get());
    }

    private double getB() {
        return N.get() * F / (F - N.get());
    }

    public double[][] calcPoint(double[][] point) {
        double[][] calcedPoint = Math.matrixProduct(MVP, point, 4, 1, 4);
        for (int i = 0; i < 3; ++i) {
            calcedPoint[0][i] /= calcedPoint[0][3];
        }
        return calcedPoint;
    }

}
