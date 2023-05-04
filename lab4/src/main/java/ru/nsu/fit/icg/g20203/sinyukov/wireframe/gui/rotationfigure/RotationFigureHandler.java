package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public interface RotationFigureHandler {

    RotationFigureParameterContainer getRotationFigureParameterContainer();

    void setRotationFigureParameterContainer(RotationFigureParameterContainer rotationFigureParameterContainer);

    void setSpline(Spline spline);
}
