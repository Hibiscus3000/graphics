package ru.nsu.fit.icg.lab1.action;

import ru.nsu.fit.icg.lab1.ColorListener;

import java.awt.*;

public class ColorAction extends PropertyAction {

    private final ColorListener colorListener;

    public ColorAction(String name, String nameInstrumentalCase, Color color, ColorListener colorListener, String iconFileName) {
        super(name,"colors/", iconFileName, "Сделать текущий цвет " + nameInstrumentalCase);
        this.colorListener = colorListener;
        putValue("Color", color);
    }

    @Override
    public void primaryActionPerformed() {
        colorListener.setColor((Color) getValue("Color"));
        setAllListenersSelected();
    }

    @Override
    public void secondaryActionPerformed() {
    }
}
