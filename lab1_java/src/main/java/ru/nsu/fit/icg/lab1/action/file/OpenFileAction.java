package ru.nsu.fit.icg.lab1.action.file;

import ru.nsu.fit.icg.lab1.PaintFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;

public class OpenFileAction extends FileAction {

    public OpenFileAction(String name, PaintFrame owner) {
        super(name, "open.png", owner);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", "bmp", "jpeg", "png", "gif"));
        int result = chooser.showOpenDialog(owner);
        if (result == JFileChooser.APPROVE_OPTION) {
            owner.openImage(chooser.getSelectedFile());
        }
    }
}
