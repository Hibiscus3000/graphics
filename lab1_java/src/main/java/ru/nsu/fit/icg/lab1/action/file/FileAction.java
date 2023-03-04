package ru.nsu.fit.icg.lab1.action.file;

import ru.nsu.fit.icg.lab1.PaintFrame;
import ru.nsu.fit.icg.lab1.action.CommandAction;

import javax.swing.*;
import java.io.File;

public abstract class FileAction extends CommandAction {

    protected final PaintFrame owner;
    protected static JFileChooser chooser = new JFileChooser();

    static {
        chooser.setCurrentDirectory(new File("."));
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
    }

    protected FileAction(String name, String iconFileName, PaintFrame owner) {
        super(name, iconFileName, name);
        this.owner = owner;
    }
}
