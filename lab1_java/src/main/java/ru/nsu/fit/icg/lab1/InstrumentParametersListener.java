package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.instrument.Instrument;

public interface InstrumentParametersListener {

    void changeInstrumentParameters(Instrument instrument);

    void setInstrument(Instrument instrument);
}
