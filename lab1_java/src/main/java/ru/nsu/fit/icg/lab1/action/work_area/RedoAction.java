package ru.nsu.fit.icg.lab1.action.work_area;

import ru.nsu.fit.icg.lab1.panel.PaintPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends WorkAreaAction {

    public RedoAction(PaintPanel paintPanel) {
        super("Восстановить", "redo.png",
                "Восстановить последнее отмененное действие",
                paintPanel);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paintPanel.redo();
    }
}
