package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import java.io.Serializable;

public class RotationFigureParameterContainer implements Serializable {

    private final double angleX;
    private final double angleY;
    private final double angleZ;
    private final double zoom;
    private final int numberOfGenerators;
    private final int numberOfLinesBetweenGenerators;


    public RotationFigureParameterContainer(double angleX, double angleY, double angleZ,
                                            double zoom, int numberOfGenerators,
                                            int numberOfLinesBetweenGenerators) {
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
        this.zoom = zoom;
        this.numberOfGenerators = numberOfGenerators;
        this.numberOfLinesBetweenGenerators = numberOfLinesBetweenGenerators;
    }

    public double getAngleX() {
        return angleX;
    }

    public double getAngleY() {
        return angleY;
    }

    public double getAngleZ() {
        return angleZ;
    }

    public double getZoom() {
        return zoom;
    }

    public int getNumberOfGenerators() {
        return numberOfGenerators;
    }

    public int getNumberOfLinesBetweenGenerators() {
        return numberOfLinesBetweenGenerators;
    }
}
