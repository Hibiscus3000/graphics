package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;

public abstract class ColoredInstrument extends Instrument {

    protected Color color;

    protected ColoredInstrument(InstrumentUser instrumentUser) {
        super(instrumentUser);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
