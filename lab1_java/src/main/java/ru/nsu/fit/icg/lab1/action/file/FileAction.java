package ru.nsu.fit.icg.lab1.action.file;

import ru.nsu.fit.icg.lab1.PaintFrame;
import ru.nsu.fit.icg.lab1.action.CommandAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public abstract class FileAction extends CommandAction {

    protected final PaintFrame owner;
    protected JFileChooser chooser = new JFileChooser();

    protected FileFilter imagesFilter = new FileNameExtensionFilter("Файлы изображений (.bmp, .jpeg, .jpg, .png, .gif)",
            "bmp", "jpeg", "jpg", "png", "gif");
    protected FileFilter bmpFilter = new FileNameExtensionFilter("Файлы .bmp", "bmp");
    protected FileFilter jpegFilter = new FileNameExtensionFilter("Файлы .jpeg", "jpeg");
    protected FileFilter jpgFilter = new FileNameExtensionFilter("Файлы .jpg", "jpg");
    protected FileFilter pngFilter = new FileNameExtensionFilter("Файлы .png", "png");
    protected FileFilter gifFilter = new FileNameExtensionFilter("Файлы .gif", "gif",
            "bmp", "jpeg", "png", "gif");

    protected FileAction(String name, String iconFileName, PaintFrame owner) {
        super(name, iconFileName, name);
        chooser.setCurrentDirectory(new File("."));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        this.owner = owner;
    }
}
