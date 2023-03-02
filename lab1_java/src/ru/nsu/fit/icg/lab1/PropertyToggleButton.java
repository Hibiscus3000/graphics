package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.action.PropertyAction;
import ru.nsu.fit.icg.lab1.action.PropertyActionListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PropertyToggleButton extends JToggleButton implements PropertyActionListener {

    public PropertyToggleButton(PropertyAction propertyAction) {
        super(propertyAction);
        setText("");
        propertyAction.addPropertyActionListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 -> propertyAction.primaryActionPerformed();
                    case MouseEvent.BUTTON3 -> propertyAction.secondaryActionPerformed();
                }
            }
        });
    }

    @Override
    public void setListenerSelected() {
        super.setSelected(true);
    }
}
