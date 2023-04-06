package ru.nsu.fit.icg.lab2;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;
import ru.nsu.fit.icg.lab2.menuToolbar.MenuToolbarBox;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterDialogShower;

import java.awt.*;

public class FilterApplication extends Application {

    private static final double initialSizeScale = 0.5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            ImageBox imageBox = new ImageBox();
            FilterChangeHandler filterChangeHandler = new FilterChangeHandler(imageBox);
            FilterDialogShower filterDialogShower = new FilterDialogShower(imageBox);
            MenuToolbarBox menuToolbarBox = new MenuToolbarBox(filterChangeHandler, filterDialogShower,
                    stage.getOwner(), imageBox);
            menuToolbarBox.addFilters();
            menuToolbarBox.addTransformations();
            VBox appBox = new VBox(menuToolbarBox, imageBox);
            Rectangle2D screenSize = Screen.getPrimary().getBounds();
            Scene scene = new Scene(appBox,
                    initialSizeScale * screenSize.getWidth(), initialSizeScale * screenSize.getHeight());
            scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
            imageBox.prefWidthProperty().bind(scene.widthProperty());
            imageBox.prefHeightProperty().bind(scene.heightProperty().subtract(menuToolbarBox.heightProperty()));
            stage.setScene(scene);
            stage.show();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}