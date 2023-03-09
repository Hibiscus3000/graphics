package ru.nsu.fit.icg.lab1.action.work_area;

import ru.nsu.fit.icg.lab1.panel.PaintPanel;

import java.awt.event.ActionEvent;

public class ClearAction extends WorkAreaAction {

    public ClearAction(PaintPanel paintPanel) {
        super("Очистка", "fill.png", "Очистка рабочей области",
                paintPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paintPanel.clear();
    }
}
