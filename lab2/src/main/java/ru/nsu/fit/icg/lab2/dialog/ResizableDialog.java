package ru.nsu.fit.icg.lab2.dialog;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.DialogPane;
import javafx.stage.Screen;
import javafx.stage.Window;

public interface ResizableDialog {
    default void resize() {
        Window window = getDialogPane().getScene().getWindow();
        window.sizeToScene();
        Double width = window.getWidth();
        Double height = window.getHeight();

        if (width.isNaN() || height.isNaN()) {
            return;
        }
        final Rectangle2D windowSize = Screen.getPrimary().getBounds();
        double centerX = (windowSize.getWidth() - width) / 2;
        double centerY = (windowSize.getHeight() - height) / 2;
        setX(centerX);
        setY(centerY);
    }

    DialogPane getDialogPane();

    void setX(double x);

    void setY(double y);
}
