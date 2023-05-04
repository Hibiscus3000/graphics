package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox;

import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.SerializableSpline;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public interface SplineHandler {

    SerializableSpline getSerializableSpline();

    void setSpline(SerializableSpline serializableSpline);

    Spline getSpline();
}
