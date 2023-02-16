package ru.nsu.fit.graphics.exercise1.action;

import ru.nsu.fit.graphics.exercise1.MyPanel;

import java.awt.*;
import java.awt.event.ActionEvent;

public class BackgroundColorAction extends ColorAction {

    public BackgroundColorAction(MyPanel myPanel, String colorName, String iconFilePath, Color color) {
        super(myPanel, colorName, iconFilePath, "background", color);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EventQueue.invokeLater(() ->myPanel.setBackground((Color)getValue("color")));
    }
}
