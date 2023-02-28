package ru.nsu.fit.icg.lab1.menu;

import ru.nsu.fit.icg.lab1.InstrumentListener;
import ru.nsu.fit.icg.lab1.instrument.StampInstrument;

import javax.swing.*;

public final class StampMenu extends ParametrisedInstrumentMenu {

    public StampMenu(StampInstrument stampInstrument, InstrumentListener instrumentListener,
                     ButtonGroup instrumentButtonGroup, boolean chosenByDefault) {
        super("Штамп", stampInstrument, instrumentListener,instrumentButtonGroup,chosenByDefault);
    }

    @Override
    protected void changeParameters() {

    }
}
