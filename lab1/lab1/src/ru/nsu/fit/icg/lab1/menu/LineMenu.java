package ru.nsu.fit.icg.lab1.menu;

import ru.nsu.fit.icg.lab1.InstrumentListener;
import ru.nsu.fit.icg.lab1.instrument.LineInstrument;

import javax.swing.*;

public final class LineMenu extends ParametrisedInstrumentMenu {

    public LineMenu(LineInstrument lineInstrument, InstrumentListener instrumentListener,
                    ButtonGroup instrumentButtonGroup, boolean chosenByDefault) {
        super("Линия", lineInstrument, instrumentListener, instrumentButtonGroup, chosenByDefault);
    }

    @Override
    protected void changeParameters() {

    }
}
