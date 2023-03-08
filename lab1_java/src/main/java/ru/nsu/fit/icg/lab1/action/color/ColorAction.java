package ru.nsu.fit.icg.lab1.action.color;

import ru.nsu.fit.icg.lab1.action.ExclusiveAction;
import ru.nsu.fit.icg.lab1.panel.ColorListener;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ColorAction extends ExclusiveAction {

    private final ColorListener colorListener;

    public ColorAction(String name, String nameInstrumentalCase, Color color, ColorListener colorListener, String iconFileName) {
        super(name, iconFileName, "Сделать текущий цвет " + nameInstrumentalCase);
        this.colorListener = colorListener;
        putValue("Color", color);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        colorListener.setColor((Color) getValue("Color"));
        setAllListenersSelected();
    }
}
