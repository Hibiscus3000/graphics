package ru.nsu.fit.icg.lab1.action;

import ru.nsu.fit.icg.lab1.ColorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ColorAction extends AbstractAction {

    private static final int colorButtonSide = 20;
    private static final String iconsDirName = "icons/";

    public ColorAction(String name, ColorListener colorListener, String iconFileName) {
        putValue(Action.SHORT_DESCRIPTION, "Сделать текущий цвет " + name);
        ImageIcon icon = new ImageIcon(iconsDirName + iconFileName);
        Image image = icon.getImage();
        icon.setImage(image.getScaledInstance(colorButtonSide, colorButtonSide, Image.SCALE_SMOOTH));
        putValue(Action.SMALL_ICON, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
