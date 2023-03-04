package ru.nsu.fit.icg.lab1.action.file;

import ru.nsu.fit.icg.lab1.PaintFrame;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class SaveFileAction extends FileAction {

    public SaveFileAction(String name, PaintFrame owner) {
        super(name, "save.png", owner);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", "png"));
        int result = chooser.showSaveDialog(owner);
        if (result == APPROVE_OPTION) {
            owner.saveImage(chooser.getSelectedFile());
        }
    }
}
