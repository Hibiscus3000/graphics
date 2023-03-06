package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.instrument.parameter.NotNecessaryValue;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.instrument.parameter.Value;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;

public abstract class ResizableInstrument extends ColoredInstrument implements MouseWheelListener {

    private final Map<String, Value> values;
    protected final int numberOfPermitsToRelease = 1;

    protected boolean getValueInUse(String valueName) {
        Value value = values.get(valueName);
        if (null == value) {
            throw new NullPointerException();
        }
        return !(value instanceof NotNecessaryValue)
                || ((NotNecessaryValue) value).useValue();
    }

    protected ResizableInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(instrumentUser);
        values = parametersParser.getValues(getClass().getName(), getParameterGroupNames());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract String[] getParameterGroupNames();

    protected abstract void changeSize(int mouseWheelClicks);

    protected int getValue(String valueName) {
        return values.get(valueName).getValue();
    }

    protected void changeValue(String valueName, int mouseWheelClicks) {
        Value value = values.get(valueName);
        value.changeValue(-value.getStep() * mouseWheelClicks);
        repaint();
    }

    protected abstract void repaint();

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        changeSize(e.getWheelRotation());
    }
}
