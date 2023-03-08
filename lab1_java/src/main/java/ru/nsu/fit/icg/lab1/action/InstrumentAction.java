package ru.nsu.fit.icg.lab1.action;

import ru.nsu.fit.icg.lab1.InstrumentParametersListener;
import ru.nsu.fit.icg.lab1.instrument.Instrument;

import java.awt.event.ActionEvent;

public class InstrumentAction extends ExclusiveAction {

    private final InstrumentParametersListener instrumentParametersListener;

    public InstrumentAction(String name,
                            InstrumentParametersListener instrumentParametersListener,
                            Instrument instrument,
                            String iconFileName) {
        super(name, "instrument/" + iconFileName, String.format("Выбрать инструмент \"%s\"", name));
        this.instrumentParametersListener = instrumentParametersListener;
        putValue("Instrument", instrument);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setAllListenersSelected();
        instrumentParametersListener.setInstrument((Instrument) getValue("Instrument"));
    }

    public void changeParameters() {
        instrumentParametersListener
                .changeInstrumentParameters((Instrument) getValue("Instrument"));
        actionPerformed(null);
    }
}
