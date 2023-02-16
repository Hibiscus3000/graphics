package ru.nsu.fit.graphics.exercise1.action;

import ru.nsu.fit.graphics.exercise1.MyPanel;

import javax.swing.*;
import java.awt.*;

public abstract class ColorAction extends AbstractAction {

    protected final MyPanel myPanel;
    private final String iconsDirPath = "icons/";

    private final int iconSide = 15;

    public ColorAction(MyPanel myPanel, String colorName, String iconFileName, String objectName,
                       Color color) {
        putValue(Action.NAME,colorName);
        ImageIcon icon = new ImageIcon(iconsDirPath + iconFileName);
        Image image = icon.getImage();
        icon.setImage(image.getScaledInstance(iconSide,iconSide,Image.SCALE_SMOOTH));
        putValue(Action.SMALL_ICON, icon);
        putValue(Action.SHORT_DESCRIPTION,String.format("Set %s color to %s",objectName,colorName));
        putValue("color",color);

        this.myPanel = myPanel;
    }
}
