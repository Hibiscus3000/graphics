package ru.nsu.fit.icg.lab1.action;

import ru.nsu.fit.icg.lab1.InstrumentListener;
import ru.nsu.fit.icg.lab1.instrument.Instrument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class InstrumentAction extends PropertyAction {

    private final InstrumentListener instrumentListener;

    public InstrumentAction(String name,InstrumentListener instrumentListener, Instrument instrument,
                            String iconFileName) {
        super(name, "instruments/",iconFileName,String.format("Выбрать инструмент \"%s\"",name));
        this.instrumentListener = instrumentListener;
        putValue("Instrument",instrument);
    }

    @Override
    public void primaryActionPerformed() {
        instrumentListener.setInstrument((Instrument)getValue("Instrument"));
        setAllListenersSelected();
    }

    @Override
    public void secondaryActionPerformed() {
        ((Instrument)getValue("Instrument")).changeProperties();
    }
}
