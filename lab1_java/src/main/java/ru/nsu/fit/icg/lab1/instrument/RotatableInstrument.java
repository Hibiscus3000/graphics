package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class RotatableInstrument extends ResizableInstrument {

    protected RotatableInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser);
    }

    @Override
    public String[] getParameterGroupNames() {
        return new String[]{"rotatable"};
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (0 != (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK)) {
            changeValue("angle", e.getWheelRotation());
        } else {
            super.mouseWheelMoved(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
