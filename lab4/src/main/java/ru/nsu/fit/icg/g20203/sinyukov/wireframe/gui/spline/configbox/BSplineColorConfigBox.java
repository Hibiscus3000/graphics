package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color.ColorContainer;

import java.util.Map;

public class BSplineColorConfigBox extends VBox {

    public void setColorContainer(ColorContainer colorContainer) {
        getChildren().clear();
        for (Map.Entry<String, ObjectProperty<Color>> colorEntry : colorContainer.getColors().entrySet()) {
            ColorPicker colorPicker = new ColorPicker(colorEntry.getValue().get());
            colorPicker.setOnAction(e -> {
                e.consume();
                colorContainer.putColor(colorEntry.getKey(), colorPicker.getValue());
            });
            Label colorLabel = new Label(colorEntry.getKey());
            HBox colorBox = new HBox(colorLabel, colorPicker);
            getChildren().add(colorBox);
        }
    }

}
