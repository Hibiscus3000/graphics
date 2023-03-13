package ru.nsu.fit.icg.lab1.menu_item;


import ru.nsu.fit.icg.lab1.action.ExclusiveActionListener;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;

import javax.swing.*;

public class InstrumentMenu extends JMenu implements ExclusiveActionListener {

    private final JRadioButtonMenuItem useItem;

    public InstrumentMenu(InstrumentAction instrumentAction, ButtonGroup buttonGroup) {
        super(instrumentAction);

        if (instrumentAction.isParameterizable()) {
            JMenuItem parametersItem = new JMenuItem("Параметры");
            parametersItem.addActionListener(e -> instrumentAction.changeParameters());
            add(parametersItem);
        }
        useItem = new JRadioButtonMenuItem("Использовать");
        useItem.addActionListener(instrumentAction);
        instrumentAction.addExclusiveActionListener(this);
        buttonGroup.add(useItem);

        addSeparator();
        add(useItem);
    }

    @Override
    public void setListenerSelected() {
        useItem.setSelected(true);
    }
}
