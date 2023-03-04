package ru.nsu.fit.icg.lab1.panel;

import ru.nsu.fit.icg.lab1.instrument.Instrument;

public interface InstrumentUser {

    void setInstrument(Instrument instrument);

    void repaintComplete();

    void repaintIncomplete();
}
