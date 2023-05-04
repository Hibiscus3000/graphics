package ru.nsu.fit.icg.g20203.sinyukov.wireframe;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure.RotationFigureHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.ColorHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.SplineHandler;

public class SaveOpenControlBox extends VBox {

    private ColorHandler colorHandler;
    private SplineHandler splineHandler;
    private RotationFigureHandler rotationFigureHandler;

    public SaveOpenControlBox() {
        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(e -> save());
        Button openButton = new Button("Открыть");
        openButton.setOnAction(e -> open());
        getChildren().addAll(saveButton, openButton);
    }

    public void setColorHandler(ColorHandler colorHandler) {
        this.colorHandler = colorHandler;
    }

    public void setSplineHandler(SplineHandler splineHandler) {
        this.splineHandler = splineHandler;
    }

    public void setRotationFigureParameterContainer(RotationFigureHandler rotationFigureHandler) {
        this.rotationFigureHandler = rotationFigureHandler;
    }

    private void save() {

    }

    private void open() {

    }
}
