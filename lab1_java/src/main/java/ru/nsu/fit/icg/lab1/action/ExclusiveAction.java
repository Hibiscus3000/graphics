package ru.nsu.fit.icg.lab1.action;

import java.util.ArrayList;
import java.util.List;

public abstract class ExclusiveAction extends CommandAction {

    protected final List<ExclusiveActionListener> exclusiveActionListeners = new ArrayList<>();

    protected ExclusiveAction(String name, String iconFileName, String shortDescription) {
        super(name, iconFileName, shortDescription);
    }

    public void addExclusiveActionListener(ExclusiveActionListener exclusiveActionListener) {
        exclusiveActionListeners.add(exclusiveActionListener);
    }

    protected void setAllListenersSelected() {
        for (ExclusiveActionListener exclusiveActionListener : exclusiveActionListeners) {
            exclusiveActionListener.setListenerSelected();
        }
    }
}
