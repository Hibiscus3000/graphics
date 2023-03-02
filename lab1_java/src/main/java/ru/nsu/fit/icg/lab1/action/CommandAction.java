package ru.nsu.fit.icg.lab1.action;

import javax.swing.*;
import java.awt.*;

public abstract class CommandAction extends AbstractAction {

    private static final int colorButtonSide = 13;

    protected CommandAction(String name, String iconFileName, String shortDescription) {
        putValue(Action.NAME,name);
        putValue(Action.SHORT_DESCRIPTION, shortDescription);
        ImageIcon icon = new ImageIcon(getClass().getResource(iconFileName));
        Image image = icon.getImage();
        icon.setImage(image.getScaledInstance(colorButtonSide, colorButtonSide, Image.SCALE_SMOOTH));
        putValue(Action.SMALL_ICON, icon);
    }
}
