package ru.nsu.fit.icg.lab2.file;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Window;
import ru.nsu.fit.icg.lab2.ImageBox;

public abstract class FileHandler implements EventHandler<ActionEvent> {

    protected final Window owner;
    protected final ImageBox imageBox;

    protected FileHandler(Window owner, ImageBox imageBox) {
        this.owner = owner;
        this.imageBox = imageBox;
    }

    public abstract String getName();

}
