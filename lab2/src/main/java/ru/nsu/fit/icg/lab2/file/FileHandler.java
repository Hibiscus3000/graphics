package ru.nsu.fit.icg.lab2.file;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Window;
import ru.nsu.fit.icg.lab2.DrawParent;

public abstract class FileHandler implements EventHandler<ActionEvent> {

    protected final Window owner;
    protected final DrawParent drawParent;

    protected FileHandler(Window owner, DrawParent drawParent) {
        this.owner = owner;
        this.drawParent = drawParent;
    }

    public abstract String getName();

}
