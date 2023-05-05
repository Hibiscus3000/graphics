package ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure;

public class Vector {

    private final double x;
    private final double y;
    private final double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector vectorProduct(Vector other) {
        return new Vector(y * other.z - other.y * z, z * other.x - other.z * x,
                x * other.y - other.x * y);
    }

    public double scalarProduct(Vector other) {
        return x * other.x + y * other.y + z * other.z;
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

    public double getNorm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector divide(double a) {
        return new Vector(x / a, y / a, z / a);
    }
}
