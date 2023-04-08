package ru.nsu.fit.icg.lab2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;
import ru.nsu.fit.icg.lab2.menuToolbar.MenuToolbarBox;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterDialogShower;

import java.awt.*;

public class FilterApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            imageBox = new ImageBox();
            FilterChangeHandler filterChangeHandler = new FilterChangeHandler(imageBox);
            FilterDialogShower filterDialogShower = new FilterDialogShower(imageBox);
            MenuToolbarBox menuToolbarBox = new MenuToolbarBox(filterChangeHandler, filterDialogShower,
                    stage.getOwner(), imageBox);
            menuToolbarBox.addFilters();
            menuToolbarBox.addTransformations();
            VBox appBox = new VBox(menuToolbarBox, imageBox);
            Scene scene = new Scene(appBox);
            scene.getStylesheets().add(getClass().getResource("styling.css").toExternalForm());
            imageBox.prefWidthProperty().bind(scene.widthProperty());
            imageBox.prefHeightProperty().bind(scene.heightProperty().subtract(menuToolbarBox.heightProperty()));
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> {
                e.consume();
                exit(stage);
            });
            stage.setMaximized(true);
            stage.setMinWidth(640);
            stage.setMinHeight(480);
            stage.show();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private ImageBox imageBox;

    private void exit(Stage stage) {
        ButtonType okButtonType = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите выйти?",
                okButtonType, cancelButtonType);
        logoutAlert.setTitle("Выход");
        logoutAlert.setHeaderText("Выход");
        if (okButtonType == logoutAlert.showAndWait().get()) {
            if (null != imageBox) {
                imageBox.stop();
            }
            stage.close();
        }
    }
}