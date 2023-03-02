package ru.nsu.fit.icg.lab1.menu_item;

import ru.nsu.fit.icg.lab1.action.ColorAction;
import ru.nsu.fit.icg.lab1.action.ExclusiveActionListener;

import javax.swing.*;

public class ColorMenuRadioButton extends JRadioButtonMenuItem implements ExclusiveActionListener {
    
    public ColorMenuRadioButton(ColorAction colorAction) {
        super(colorAction);
        colorAction.addExclusiveActionListener(this);
    }

    @Override
    public void setListenerSelected() {
        setSelected(true);
    }
}
