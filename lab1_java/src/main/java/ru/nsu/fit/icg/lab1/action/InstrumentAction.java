package ru.nsu.fit.icg.lab1.action;

import ru.nsu.fit.icg.lab1.instrument.Instrument;
import ru.nsu.fit.icg.lab1.instrument.InstrumentParametersListener;
import ru.nsu.fit.icg.lab1.panel.ColorSelectionListener;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.event.ActionEvent;

public class InstrumentAction extends ExclusiveAction {

    private final InstrumentUser instrumentUser;
    private final InstrumentParametersListener instrumentParametersListener;
    private final ColorSelectionListener colorSelectionListener;

    public InstrumentAction(String name,
                            InstrumentUser instrumentUser,
                            InstrumentParametersListener instrumentParametersListener,
                            ColorSelectionListener colorSelectionListener,
                            Instrument instrument,
                            String iconFileName) {
        super(name, "instrument/" + iconFileName, String.format("Выбрать инструмент \"%s\"", name));
        this.instrumentUser = instrumentUser;
        this.instrumentParametersListener = instrumentParametersListener;
        this.colorSelectionListener = colorSelectionListener;
        putValue("Instrument", instrument);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setAllListenersSelected();
        instrumentUser.setInstrument((Instrument) getValue("Instrument"));
        colorSelectionListener.clearColorSelection();
    }

    public void changeParameters() {
        instrumentParametersListener
                .changeInstrumentParameters((Instrument) getValue("Instrument"));
    }
}
