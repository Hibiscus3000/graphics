package ru.nsu.fit.icg.g20203.sinyukov.wireframe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.BSplinePane;

public class WireFrameApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.setScene(new Scene(new BSplinePane()));
        stage.show();
    }
}
