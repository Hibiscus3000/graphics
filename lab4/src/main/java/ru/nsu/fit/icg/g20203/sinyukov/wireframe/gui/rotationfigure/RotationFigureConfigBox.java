package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.SaveOpenControlBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class RotationFigureConfigBox extends VBox implements RotationFigureHandler {

    public RotationFigureConfigBox(Button changeScenesButton, SaveOpenControlBox saveOpenControlBox) {
        getChildren().addAll(saveOpenControlBox, changeScenesButton);
    }

    @Override
    public RotationFigureParameterContainer getRotationFigureParameterContainer() {
        return null;
    }

    @Override
    public void setRotationFigureParameterContainer(RotationFigureParameterContainer rotationFigureParameterContainer) {

    }

    @Override
    public void setSpline(Spline spline) {

    }
}
