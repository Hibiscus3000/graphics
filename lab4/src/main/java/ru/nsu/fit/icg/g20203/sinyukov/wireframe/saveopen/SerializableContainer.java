package ru.nsu.fit.icg.g20203.sinyukov.wireframe.saveopen;

import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure.RotationFigureParameterContainer;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.SerializableSpline;

import java.io.Serializable;

public class SerializableContainer implements Serializable {

    private final SerializableSpline serializableSpline;
    private final RotationFigureParameterContainer rotationFigureParameterContainer;


    public SerializableContainer(SerializableSpline serializableSpline,
                                 RotationFigureParameterContainer rotationFigureParameterContainer) {
        this.serializableSpline = serializableSpline;
        this.rotationFigureParameterContainer = rotationFigureParameterContainer;
    }

    public SerializableSpline getSerializableSpline() {
        return serializableSpline;
    }

    public RotationFigureParameterContainer getRotationFigureParameterContainer() {
        return rotationFigureParameterContainer;
    }
}
