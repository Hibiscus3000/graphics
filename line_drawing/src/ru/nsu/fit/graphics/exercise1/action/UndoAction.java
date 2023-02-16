package ru.nsu.fit.graphics.exercise1.action;

import ru.nsu.fit.graphics.exercise1.MyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {

    private final MyPanel myPanel;

    public UndoAction(MyPanel myPanel) {
        putValue(Action.NAME,"Undo");
        putValue(Action.SHORT_DESCRIPTION,"Remove last drawn line");

        this.myPanel = myPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        myPanel.undo();
    }
}
