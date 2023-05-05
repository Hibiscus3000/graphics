package ru.nsu.fit.icg.g20203.sinyukov.wireframe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure.RotationFigureHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure.RotationFigureSplitPane;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.BSplinePane;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.ColorHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.SplineHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.saveopen.SaveOpenControlBox;

public class WireFrameApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMinWidth(640);
        stage.setMinHeight(480);

        Button changeToRotationFigureScene = new Button("Визуализация");
        Button changeToSplineScene = new Button("Редактор B-сплайна");
        SaveOpenControlBox saveOpenCBSpline = new SaveOpenControlBox();
        SaveOpenControlBox saveOpenCBRotationFig = new SaveOpenControlBox();

        BSplinePane splinePane = new BSplinePane(changeToRotationFigureScene, saveOpenCBSpline);
        Scene splineScene = new Scene(splinePane, 900, 600);
        RotationFigureSplitPane rotationFigureSplitPane =
                new RotationFigureSplitPane(changeToSplineScene, saveOpenCBRotationFig);
        Scene rotationFigureScene = new Scene(rotationFigureSplitPane, 900, 600);

        SplineHandler splineHandler = splinePane.getSplineHandler();
        ColorHandler colorHandler = splinePane.getColorHandler();
        RotationFigureHandler rotationFigureHandler =
                rotationFigureSplitPane.getRotationFigureHandler();

        saveOpenCBSpline.setSplineHandler(splineHandler);
        saveOpenCBSpline.setColorHandler(colorHandler);
        saveOpenCBSpline.setRotationFigureParameterContainer(rotationFigureHandler);
        saveOpenCBRotationFig.setSplineHandler(splineHandler);
        saveOpenCBRotationFig.setColorHandler(colorHandler);
        saveOpenCBRotationFig.setRotationFigureParameterContainer(rotationFigureHandler);

        changeToRotationFigureScene.setOnAction(e -> {
            rotationFigureHandler.setSpline(splineHandler.getSpline());
            stage.setScene(rotationFigureScene);
        });
        changeToSplineScene.setOnAction(e -> {
            stage.setScene(splineScene);
        });

        stage.setScene(splineScene);
        stage.show();
    }
}
