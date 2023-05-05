package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.MVP;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.saveopen.SaveOpenControlBox;

public class RotationFigureSplitPane extends SplitPane {

    private final RotationFigureConfigBox rotationFigureConfigBox;

    public RotationFigureSplitPane(Button changeScenesButton, SaveOpenControlBox saveOpenControlBox) {
        MVP mvp = new MVP();
        RotationFigurePane rotationFigurePane = new RotationFigurePane(mvp);
        rotationFigureConfigBox = new RotationFigureConfigBox(changeScenesButton, saveOpenControlBox,
                rotationFigurePane, mvp);
        getItems().addAll(rotationFigurePane, rotationFigureConfigBox);
    }

    public RotationFigureHandler getRotationFigureHandler() {
        return rotationFigureConfigBox;
    }
}
