package ru.nsu.fit.icg.lab1.menu_item;


import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.action.ExclusiveActionListener;

import javax.swing.*;

public class InstrumentMenu extends JMenu implements ExclusiveActionListener {

    private final JRadioButtonMenuItem useItem;

    public InstrumentMenu(InstrumentAction instrumentAction, ButtonGroup buttonGroup) {
        super(instrumentAction);

        JMenuItem parametersItem = new JMenuItem("Параметры");
        parametersItem.addActionListener(e -> instrumentAction.changeParameters());
        useItem = new JRadioButtonMenuItem("Использовать");
        useItem.addActionListener(instrumentAction);
        instrumentAction.addExclusiveActionListener(this);
        buttonGroup.add(useItem);

        add(parametersItem);
        addSeparator();
        add(useItem);
    }

    @Override
    public void setListenerSelected() {
        useItem.setSelected(true);
    }
}
