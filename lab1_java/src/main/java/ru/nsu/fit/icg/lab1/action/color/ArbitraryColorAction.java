package ru.nsu.fit.icg.lab1.action.color;

import ru.nsu.fit.icg.lab1.action.ExclusiveAction;
import ru.nsu.fit.icg.lab1.panel.ColorListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ArbitraryColorAction extends ExclusiveAction {

    private final ColorListener colorListener;
    private final JColorChooser colorChooser = new JColorChooser();

    public ArbitraryColorAction(ColorListener colorListener) {
        super("Произвольный цвет", "palette.png", "Выбор произвольного цвета");
        this.colorListener = colorListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        colorListener.showColorDialog(colorChooser);
        setAllListenersSelected();
    }
}
