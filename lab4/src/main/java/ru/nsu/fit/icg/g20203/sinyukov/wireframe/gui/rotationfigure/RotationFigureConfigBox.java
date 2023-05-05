package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.SaveOpenControlBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox.DoubleValueEditBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox.IntegerValueEditBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield.IntegerTextField;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.MVP;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.RotationFigure;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class RotationFigureConfigBox extends VBox implements RotationFigureHandler {

    private final RotationFigurePane rotationFigurePane;
    private final MVP mvp;
    private Spline spline;

    private final IntegerProperty numberOfGenerators = new SimpleIntegerProperty(5);
    private final IntegerProperty numberOfLinesBetweenGenerators = new SimpleIntegerProperty(5);
    private final IntegerProperty angleX = new SimpleIntegerProperty();
    private final IntegerProperty angleY = new SimpleIntegerProperty();
    private final IntegerProperty angleZ = new SimpleIntegerProperty();
    private final DoubleProperty zoom = new SimpleDoubleProperty();
    private final ColorPicker lineColorPicker = new ColorPicker(Color.WHITE);
    private final ColorPicker backGroundColorPicker = new ColorPicker(Color.rgb(50, 50, 50));

    public RotationFigureConfigBox(Button changeScenesButton, SaveOpenControlBox saveOpenControlBox,
                                   RotationFigurePane rotationFigurePane, MVP mvp) {
        this.rotationFigurePane = rotationFigurePane;
        this.mvp = mvp;
        IntegerTextField numberOfGeneratorsTextField = new IntegerTextField(
                "Количество образующих", numberOfGenerators,
                2, 25, 1);
        IntegerTextField numbOfLinesBetweenGensTextField = new IntegerTextField(
                "Количество линий между образующими", numberOfLinesBetweenGenerators,
                1, 100, 1);
        IntegerValueEditBox angleXEditBox = new IntegerValueEditBox(
                "Угол относительно оси x", angleX,
                (int) Math.toDegrees(MVP.minAngle), (int) Math.toDegrees(MVP.maxAngle), 5);
        IntegerValueEditBox angleYEditBox = new IntegerValueEditBox(
                "Угол относительно оси y", angleY,
                (int) Math.toDegrees(MVP.minAngle), (int) Math.toDegrees(MVP.maxAngle), 5);
        IntegerValueEditBox angleZEditBox = new IntegerValueEditBox(
                "Угол относительно оси z", angleZ,
                (int) Math.toDegrees(MVP.minAngle), (int) Math.toDegrees(MVP.maxAngle), 5);
        DoubleValueEditBox zoomEditBox = new DoubleValueEditBox("Зум",
                zoom, MVP.Nmin, MVP.Nmax, 0.1);

        mvp.angleXProperty().addListener(((observable, oldVal, newVal) -> angleX.set((int) Math.toDegrees(newVal.doubleValue()))));
        mvp.angleYProperty().addListener(((observable, oldVal, newVal) -> angleY.set((int) Math.toDegrees(newVal.doubleValue()))));
        mvp.angleZProperty().addListener(((observable, oldVal, newVal) -> angleZ.set((int) Math.toDegrees(newVal.doubleValue()))));
        mvp.nProperty().addListener(((observable, oldVal, newVal) -> zoom.set(newVal.doubleValue())));
        angleX.set((int) Math.toDegrees(mvp.getAngleX()));
        angleY.set((int) Math.toDegrees(mvp.getAngleY()));
        angleZ.set((int) Math.toDegrees(mvp.getAngleZ()));
        zoom.set(mvp.getN());

        ChangeListener<? super Number> newRotFigListener = (observable, oldVal, newVal) ->
                setSpline(spline);
        numberOfGenerators.addListener(newRotFigListener);
        numberOfLinesBetweenGenerators.addListener(newRotFigListener);

        zoom.addListener(((observableValue, oldVal, newVal) -> {
            mvp.setN(newVal.doubleValue());
            mvp.updateP();
            rotationFigurePane.redraw();
        }));

        angleX.addListener((observable, oldVal, newVal) -> {
            mvp.setAngleX(Math.toRadians(newVal.doubleValue()));
            mvp.updateM();
            rotationFigurePane.redraw();
        });

        angleY.addListener((observable, oldVal, newVal) -> {
            mvp.setAngleY(Math.toRadians(newVal.doubleValue()));
            mvp.updateM();
            rotationFigurePane.redraw();
        });

        angleZ.addListener((observable, oldVal, newVal) -> {
            mvp.setAngleZ(Math.toRadians(newVal.doubleValue()));
            mvp.updateM();
            rotationFigurePane.redraw();
        });

        DoubleValueEditBox strokeWidthEditBox = new DoubleValueEditBox("Толщина линии",
                rotationFigurePane.strokeWidthProperty(), 0.5, 5.0, 0.1);

        backGroundColorPicker.setOnAction(e -> {
            e.consume();
            rotationFigurePane.setBackgroundColor(backGroundColorPicker.getValue());
        });

        lineColorPicker.setOnAction(e -> {
            e.consume();
            rotationFigurePane.setLinesColor(lineColorPicker.getValue());
        });

        rotationFigurePane.setLinesColor(lineColorPicker.getValue());
        rotationFigurePane.setBackgroundColor(backGroundColorPicker.getValue());

        getChildren().addAll(numberOfGeneratorsTextField, numbOfLinesBetweenGensTextField,
                angleXEditBox, angleYEditBox, angleZEditBox, zoomEditBox, strokeWidthEditBox,
                new Label("Цвет линий"), lineColorPicker,
                new Label("Цвет заднего фона"), backGroundColorPicker,
                saveOpenControlBox, changeScenesButton);
    }

    @Override
    public RotationFigureParameterContainer getRotationFigureParameterContainer() {
        return null;
    }

    @Override
    public void setRotationFigureParameterContainer(RotationFigureParameterContainer
                                                            rotationFigureParameterContainer) {

    }

    @Override
    public void setSpline(Spline spline) {
        this.spline = spline;
        rotationFigurePane.setRotationFigure(new RotationFigure(numberOfGenerators.get(),
                numberOfLinesBetweenGenerators.get(), spline));
    }
}
