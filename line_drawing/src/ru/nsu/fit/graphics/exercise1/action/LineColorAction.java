package ru.nsu.fit.graphics.exercise1.action;

import ru.nsu.fit.graphics.exercise1.MyPanel;

import java.awt.*;
import java.awt.event.ActionEvent;

public class LineColorAction extends ColorAction {

    public LineColorAction(MyPanel myPanel, String colorName, String iconFilePath, Color color) {
        super(myPanel, colorName, iconFilePath, "line", color);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Color c = (Color) getValue("color");
        myPanel.setLineColor(c);
    }
}
