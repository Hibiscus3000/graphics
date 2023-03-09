package ru.nsu.fit.icg.lab1.action.work_area;

import ru.nsu.fit.icg.lab1.action.CommandAction;
import ru.nsu.fit.icg.lab1.panel.PaintPanel;

public abstract class WorkAreaAction extends CommandAction {

    protected final PaintPanel paintPanel;

    protected WorkAreaAction(String name, String iconFileName, String shortDescription,
                             PaintPanel paintPanel) {
        super(name, iconFileName, shortDescription);
        this.paintPanel = paintPanel;
    }
}
