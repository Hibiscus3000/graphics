package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point;

public class Point2D {

    private final double u;
    private final double v;

    public Point2D(double u, double v) {
        this.u = u;
        this.v = v;
    }

    public Point2D(double[][] point4D) {
        u = point4D[0][0];
        v = point4D[1][0];
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }
}
