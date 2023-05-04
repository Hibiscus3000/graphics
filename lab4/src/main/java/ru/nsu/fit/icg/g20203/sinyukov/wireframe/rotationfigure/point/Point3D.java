package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point;

import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.Vector;

public class Point3D {

    private double x;
    private double y;
    private double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector getVector() {
        return new Vector(x, y, z);
    }

    public Point3D subtract(Point3D other) {
        return new Point3D(x - other.x, y - other.y, z - other.z);
    }

    public void divide(double a) {
        x /= a;
        y /= a;
        z /= a;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double[][] get4DVector() {
        return new double[][]{{x}, {y}, {z}, {1}};
    }
}
