package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Math;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point3D;

public class MVP {

    private final ReadOnlyDoubleWrapper angleX = new ReadOnlyDoubleWrapper(0);
    private final ReadOnlyDoubleWrapper angleY = new ReadOnlyDoubleWrapper(0);
    private final ReadOnlyDoubleWrapper angleZ = new ReadOnlyDoubleWrapper(0);

    public final static double minAngle = -java.lang.Math.PI;
    public final static double maxAngle = java.lang.Math.PI;

    private double[][] M = new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}};

    private final double[][] shift = new double[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, -5},
            {0, 0, 0, 1}};

    private final Point3D lookAt = new Point3D(10, 0, 0);
    private final Point3D eye = new Point3D(-10, 0, 0);
    private final Point3D up = new Point3D(0, 0, 1);

    // (i, j, k) - basis vector in eye coordinate system
    private final Vector i;
    private final Vector j;
    private final Vector k;

    private final double[][] V;

    // near and far clipping panes
    private ReadOnlyDoubleWrapper N = new ReadOnlyDoubleWrapper(defaultN);
    private final static double defaultN = 5.5;
    public final static double Nmin = 0.1;
    public final static double Nmax = 100;
    private final double F = 20;

    private double[][] P;
    private double[][] MVP;

    public MVP() {
        Point3D lookAtMinusEye = eye.subtract(lookAt);
        k = lookAtMinusEye.divide(lookAtMinusEye.getNorm()).getVector();
        Vector upVectorProductK = up.getVector().vectorProduct(k);
        i = upVectorProductK.divide(upVectorProductK.getNorm());
        j = k.vectorProduct(i);

        Vector eyeVector = eye.getVector();
        double x0 = -i.scalarProduct(eyeVector);
        double y0 = -j.scalarProduct(eyeVector);
        double z0 = -k.scalarProduct(eyeVector);
        V = new double[][]{
                {i.getX(), i.getY(), i.getZ(), x0},
                {j.getX(), j.getY(), j.getZ(), y0},
                {k.getX(), k.getY(), k.getZ(), z0},
                {0, 0, 0, 1}};
        updateP();
        updateM();
    }

    public void updateP() {
        P = new double[][]{
                {N.get(), 0, 0, 0},
                {0, N.get(), 0, 0},
                {0, 0, getA(), getB()},
                {0, 0, 1, 0}};
        updateMVP();
    }

    public void updateM() {
        double cosXAng = java.lang.Math.cos(angleX.get());
        double sinXAng = java.lang.Math.sin(angleX.get());
        double[][] Rx = new double[][]{
                {1, 0, 0, 0},
                {0, cosXAng, -sinXAng, 0},
                {0, sinXAng, cosXAng, 0},
                {0, 0, 0, 1}};
        double cosYAng = java.lang.Math.cos(angleY.get());
        double sinYAng = java.lang.Math.sin(angleY.get());
        double[][] Ry = new double[][]{
                {cosYAng, 0, sinYAng, 0},
                {0, 1, 0, 0},
                {-sinYAng, 0, cosYAng, 0},
                {0, 0, 0, 1}};
        double cosZAng = java.lang.Math.cos(angleZ.get());
        double sinZAng = java.lang.Math.sin(angleZ.get());
        double[][] Rz = new double[][]{
                {cosZAng, -sinZAng, 0, 0},
                {sinZAng, cosZAng, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        M = Math.matrixProduct(Math.matrixProduct(Rx, Ry, 4, 4, 4),
                Rz, 4, 4, 4);
        updateMVP();
    }

    private void updateMVP() {
        MVP = Math.matrixProduct(Math.matrixProduct(P,
                        Math.matrixProduct(shift, V, 4, 4, 4), 4, 4, 4),
                M, 4, 4, 4);
    }

    private double getA() {
        return F / (F - N.get());
    }

    private double getB() {
        return -N.get() * F / (F - N.get());
    }

    public double[][] calcPoint(double[][] point) {
        double[][] calcedPoint = Math.matrixProduct(MVP, point, 4, 4, 1);

        for (int i = 0; i < 4; ++i) {
            calcedPoint[i][0] /= calcedPoint[3][0];
        }
        return calcedPoint;
    }

    public void changeN(double dN) {
        setN(N.get() + dN);
    }

    public void changeAngle(double dx, double dy, double dz) {
        setAngleX(angleX.get() + dx);
        setAngleY(angleY.get() + dy);
        setAngleZ(angleZ.get() + dz);
    }

    public void setN(double N) {
        this.N.set(java.lang.Math.max(Nmin, java.lang.Math.min(N, Nmax)));
    }

    public void setAngleX(double angleX) {
        setAngle(this.angleX, angleX);
    }

    public void setAngleY(double angleY) {
        setAngle(this.angleY, angleY);
    }

    public void setAngleZ(double angleZ) {
        setAngle(this.angleZ, angleZ);
    }

    private void setAngle(DoubleProperty angleProperty, double angleVal) {
        if (angleVal > maxAngle) {
            angleProperty.set(minAngle + (angleVal - maxAngle));
            return;
        }
        if (angleVal < minAngle) {
            angleProperty.set(maxAngle + (angleVal - minAngle));
            return;
        }
        angleProperty.set(angleVal);
    }

    public double getAngleX() {
        return angleX.get();
    }

    public double getAngleY() {
        return angleY.get();
    }

    public double getAngleZ() {
        return angleZ.get();
    }

    public double getN() {
        return N.get();
    }

    public ReadOnlyDoubleProperty angleXProperty() {
        return angleX.getReadOnlyProperty();
    }

    public ReadOnlyDoubleProperty angleYProperty() {
        return angleY.getReadOnlyProperty();
    }

    public ReadOnlyDoubleProperty angleZProperty() {
        return angleZ.getReadOnlyProperty();
    }

    public ReadOnlyDoubleProperty nProperty() {
        return N.getReadOnlyProperty();
    }

    public double getDefaultN() {
        return defaultN;
    }
}
