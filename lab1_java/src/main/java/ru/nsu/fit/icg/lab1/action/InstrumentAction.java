package ru.nsu.fit.icg.lab1.action;

import ru.nsu.fit.icg.lab1.InstrumentListener;
import ru.nsu.fit.icg.lab1.instrument.Instrument;

import java.awt.event.ActionEvent;

public class InstrumentAction extends ExclusiveAction {

    private final InstrumentListener instrumentListener;

    public InstrumentAction(String name,InstrumentListener instrumentListener, Instrument instrument,
                            String iconFileName) {
        super(name, "instrument/" + iconFileName,String.format("Выбрать инструмент \"%s\"",name));
        this.instrumentListener = instrumentListener;
        putValue("Instrument",instrument);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setAllListenersSelected();
    }

    public void changeParameters() {

    }
}
