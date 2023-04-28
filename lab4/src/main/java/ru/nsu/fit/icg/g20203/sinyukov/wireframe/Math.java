package ru.nsu.fit.icg.g20203.sinyukov.wireframe;

public class Math {

    public static double getAngle(double u, double v) {
        double angle;
        if (u > 0) {
            angle = java.lang.Math.atan(v / u);
        } else {
            if (u < 0) {
                if (v >= 0) {
                    angle = java.lang.Math.PI + java.lang.Math.atan(v / u);
                } else {
                    angle = -java.lang.Math.PI + java.lang.Math.atan(v / u);
                }
            } else {
                angle = java.lang.Math.signum(v) * java.lang.Math.PI / 2;
            }
        }
        return angle;
    }

    // A - nxl, B - lxm
    public static double[][] matrixProduct(double[][] A, double B[][], int n, int l, int m) {
        double[][] C = new double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < l; ++j) {
                for (int k = 0; k < m; ++k) {
                    C[i][k] += A[i][j] * B[j][k];
                }
            }
        }
        return C;
    }

    // v - 1xn, A - nxl
    public static double[] lineMatrixProduct(double[] v, double[][] A, int n, int l) {
        double[] x = new double[n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < l; ++j) {
                x[j] += v[i] * A[i][j];
            }
        }
        return x;
    }

    public static double[][] scalarMatrixProduct(double scalar, double[][] A, int n, int m) {
        double C[][] = new double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                C[i][j] = scalar * A[i][j];
            }
        }
        return C;
    }
}
