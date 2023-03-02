package ru.nsu.fit.icg.lab1.toggle_button;

import ru.nsu.fit.icg.lab1.action.ExclusiveAction;
import ru.nsu.fit.icg.lab1.action.ExclusiveActionListener;

import javax.swing.*;

public class ExclusiveToggleButton extends JToggleButton implements ExclusiveActionListener {

    public ExclusiveToggleButton(ExclusiveAction exclusiveAction) {
        super(exclusiveAction);
        setText("");
        exclusiveAction.addExclusiveActionListener(this);
    }

    @Override
    public void setListenerSelected() {
        setSelected(true);
    }
}
