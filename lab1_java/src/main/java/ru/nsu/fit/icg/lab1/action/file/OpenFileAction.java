package ru.nsu.fit.icg.lab1.action.file;

import ru.nsu.fit.icg.lab1.PaintFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpenFileAction extends FileAction {

    public OpenFileAction(String name, PaintFrame owner) {
        super(name, "open.png", owner);
        chooser.addChoosableFileFilter(imagesFilter);
        chooser.addChoosableFileFilter(bmpFilter);
        chooser.addChoosableFileFilter(jpegFilter);
        chooser.addChoosableFileFilter(jpgFilter);
        chooser.addChoosableFileFilter(pngFilter);
        chooser.addChoosableFileFilter(gifFilter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = chooser.showOpenDialog(owner);
        if (result == JFileChooser.APPROVE_OPTION) {
            owner.openImage(chooser.getSelectedFile());
        }
    }
}
