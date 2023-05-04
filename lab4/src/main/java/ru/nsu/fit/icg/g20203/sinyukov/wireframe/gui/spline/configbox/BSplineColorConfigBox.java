package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor.BSplineEditor;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.container.ColorContainer;

import java.util.Map;

public class BSplineColorConfigBox extends VBox implements ColorHandler {

    private ColorContainer colorContainer;
    private final BSplineEditor bSplineEditor;

    public BSplineColorConfigBox(BSplineEditor bSplineEditor) {
        this.bSplineEditor = bSplineEditor;
    }

    @Override
    public void setColorContainer(ColorContainer colorContainer) {
        getChildren().clear();
        for (Map.Entry<String, Color> colorEntry : colorContainer.getAllContained().entrySet()) {
            ColorPicker colorPicker = new ColorPicker(colorEntry.getValue());
            String colorName = colorEntry.getKey();
            colorPicker.setOnAction(e -> {
                e.consume();
                Color color = colorPicker.getValue();
                colorContainer.putInContainer(colorName, color);
                bSplineEditor.setColor(colorName, color);
            });
            Label colorLabel = new Label(colorName);
            HBox colorBox = new HBox(colorLabel, colorPicker);
            getChildren().add(colorBox);
        }
    }

    @Override
    public ColorContainer getColorContainer() {
        return colorContainer;
    }

}
