package ru.nsu.fit.icg.lab1.menu;

import ru.nsu.fit.icg.lab1.InstrumentListener;
import ru.nsu.fit.icg.lab1.PaintPanel;
import ru.nsu.fit.icg.lab1.instrument.FillInstrument;

import javax.swing.*;

public final class FillMenu extends ParametrisedInstrumentMenu {

    public FillMenu(FillInstrument fillInstrument, InstrumentListener instrumentListener,
            ButtonGroup instrumentButtonGroup, boolean chosenByDefault) {
        super("Заливка", fillInstrument, instrumentListener,instrumentButtonGroup, chosenByDefault);
    }

    @Override
    protected void changeParameters() {

    }
}
