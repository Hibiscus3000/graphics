package ru.nsu.fit.icg.lab1.menu;


import ru.nsu.fit.icg.lab1.InstrumentListener;
import ru.nsu.fit.icg.lab1.PaintPanel;
import ru.nsu.fit.icg.lab1.instrument.Instrument;

import javax.swing.*;

public abstract class ParametrisedInstrumentMenu extends JMenu {

    private final InstrumentListener instrumentListener;
    protected Instrument instrument;

    private final JRadioButtonMenuItem useItem;

    protected ParametrisedInstrumentMenu(String menuName, Instrument instrument,
                                         InstrumentListener instrumentListener,
                                         ButtonGroup instrumentButtonGroup,
                                         boolean chosenByDefault) {
        super(menuName);
        this.instrument = instrument;
        this.instrumentListener = instrumentListener;

        JMenuItem parametersItem = new JMenuItem("Параметры");
        parametersItem.addActionListener(e -> changeParameters());
        useItem = new JRadioButtonMenuItem("Использовать");
        instrumentButtonGroup.add(useItem);
        useItem.addActionListener(e -> {
            instrumentListener.setInstrument(instrument);
            instrument.setSelected();
        });
        useItem.setSelected(chosenByDefault);
        if (chosenByDefault) {
            useItem.doClick();
        }

        add(parametersItem);
        addSeparator();
        add(useItem);
    }

    protected abstract void changeParameters();

}
