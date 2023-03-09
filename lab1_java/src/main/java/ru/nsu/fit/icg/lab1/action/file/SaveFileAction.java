package ru.nsu.fit.icg.lab1.action.file;

import ru.nsu.fit.icg.lab1.PaintFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.NO_OPTION;

public class SaveFileAction extends FileAction {

    private String fileName = "Безымянный";

    public SaveFileAction(String name, PaintFrame owner) {
        super(name, "save.png", owner);
        chooser.addChoosableFileFilter(pngFilter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooser.setSelectedFile(new File(fileName));
        int result = chooser.showSaveDialog(owner);
        if (result == APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            File targetFile = new File(selectedFile.getAbsolutePath() + ".png");
            fileName = selectedFile.getName();
            if (targetFile.exists()) {
                if (NO_OPTION == JOptionPane.showConfirmDialog(owner,
                        "Файл с именем " + targetFile.getName() + " уже существует." +
                                " Вы уверены, что хотите перезаписать его?",
                        "Предупреждение: сохранение файла",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE)) {
                    actionPerformed(e);
                    return;
                }
            }
            owner.saveImage(targetFile);
        }
    }
}
