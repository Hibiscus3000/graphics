package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class BSplineEditorBox extends VBox {

    public BSplineEditorBox(BSplineEditor bSplineEditor) {
        Label cursorPositionLabel = new Label();
        EventHandler<MouseEvent> mouseEventEventHandler = e -> {
            double u = (e.getX() - widthProperty().get() / 2)
                    / bSplineEditor.scaleProperty().get() + bSplineEditor.uCenterProperty().get();
            double v = (e.getY() - heightProperty().get() / 2)
                    / bSplineEditor.scaleProperty().multiply(-1).get() - bSplineEditor.vCenterProperty().get();
            cursorPositionLabel.setText(String.format("u: %.2f v: %.2f", u, v));
        };
        bSplineEditor.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEventEventHandler);
        bSplineEditor.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEventEventHandler);
        bSplineEditor.setOnMouseExited(e -> {
            cursorPositionLabel.setText("");
        });
        getChildren().addAll(bSplineEditor, cursorPositionLabel);
    }
}
