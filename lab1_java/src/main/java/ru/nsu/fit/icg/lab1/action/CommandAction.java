package ru.nsu.fit.icg.lab1.action;

import javax.swing.*;

public abstract class CommandAction extends AbstractAction {

    protected CommandAction(String name, String iconFileName, String shortDescription) {
        putValue(Action.NAME, name);
        putValue(Action.SHORT_DESCRIPTION, shortDescription);
        putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(iconFileName)));
    }
}
