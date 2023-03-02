package ru.nsu.fit.icg.lab1.action;

import javax.swing.*;
import java.awt.*;

public abstract class MyAction extends AbstractAction {

    private static final String iconsDirName = "icons/";
    private static final int colorButtonSide = 13;

    protected MyAction(String name, String subDirName, String iconFileName, String shortDescription) {
        putValue(Action.NAME,name);
        putValue(Action.SHORT_DESCRIPTION, shortDescription);
        ImageIcon icon = new ImageIcon(iconsDirName + subDirName + iconFileName);
        Image image = icon.getImage();
        icon.setImage(image.getScaledInstance(colorButtonSide, colorButtonSide, Image.SCALE_SMOOTH));
        putValue(Action.SMALL_ICON, icon);
    }
}
