package ru.nsu.fit.icg.lab1.action.work_area;

import ru.nsu.fit.icg.lab1.panel.PaintPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends WorkAreaAction {

    public UndoAction(PaintPanel paintPanel) {
        super("Отменить", "fill.png", "Отменить последнее действие",
                paintPanel);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paintPanel.undo();
    }
}
