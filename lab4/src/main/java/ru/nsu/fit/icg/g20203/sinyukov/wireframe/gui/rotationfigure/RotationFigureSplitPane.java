package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.SaveOpenControlBox;

public class RotationFigureSplitPane extends SplitPane {

    private final RotationFigureConfigBox rotationFigureConfigBox;

    public RotationFigureSplitPane(Button changeScenesButton, SaveOpenControlBox saveOpenControlBox) {
        rotationFigureConfigBox = new RotationFigureConfigBox(changeScenesButton, saveOpenControlBox);
        getItems().addAll(new RotationFigurePane(), rotationFigureConfigBox);
    }

    public RotationFigureHandler getRotationFigureHandler() {
        return rotationFigureConfigBox;
    }
}
