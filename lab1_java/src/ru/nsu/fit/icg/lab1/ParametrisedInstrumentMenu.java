package ru.nsu.fit.icg.lab1;


import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.action.PropertyAction;
import ru.nsu.fit.icg.lab1.action.PropertyActionListener;

import javax.swing.*;

public class ParametrisedInstrumentMenu extends JMenu implements PropertyActionListener {

    private final JRadioButtonMenuItem useItem;

    protected ParametrisedInstrumentMenu(InstrumentAction instrumentAction, ButtonGroup buttonGroup) {
        super(instrumentAction);

        JMenuItem parametersItem = new JMenuItem("Параметры");
        parametersItem.addActionListener(e -> instrumentAction.secondaryActionPerformed());
        useItem = new JRadioButtonMenuItem("Использовать");
        useItem.addActionListener(e -> instrumentAction.primaryActionPerformed());
        instrumentAction.addPropertyActionListener(this);
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
