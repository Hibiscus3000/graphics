package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.instrument.Instrument;

import javax.swing.*;

public class PaintPanel extends JPanel {

    private Instrument instrument;

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

}
