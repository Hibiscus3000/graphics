package ru.nsu.fit.icg.lab1.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class PropertyAction extends MyAction{

    protected final List<PropertyActionListener> propertyActionListeners = new ArrayList<>();

    protected PropertyAction(String name, String subDirName, String iconFileName, String shortDescription) {
        super(name, subDirName, iconFileName, shortDescription);
    }

    public void addPropertyActionListener(PropertyActionListener propertyActionListener) {
        propertyActionListeners.add(propertyActionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public abstract void primaryActionPerformed();

    public abstract void secondaryActionPerformed();

    protected void setAllListenersSelected() {
        for (PropertyActionListener propertyActionListener : propertyActionListeners) {
            propertyActionListener.setListenerSelected();
        }
    }
}
