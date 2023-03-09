package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.NotNecessaryValue;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.instrument.parameter.Value;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class ParameterizableInstrument extends ColoredInstrument implements MouseWheelListener {

    private final Map<String, Value> values;
    protected final int numberOfPermitsToRelease = 1;

    protected final List<ValueListener> valueListenerList = new ArrayList<>();

    private static Properties properties = new Properties();

    static {
        var stream = ParameterizableInstrument.class.getResourceAsStream("value_listener.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected ParameterizableInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser,
                                        String... valueNames) {
        super(instrumentUser);
        for (String valueName : valueNames) {
            valueListenerList.add(getValueListener(valueName));
        }
        values = parametersParser.getValues(getClass().getName(),
                getParameterGroupNames());
    }

    private ValueListener getValueListener(String valueName) {
        try {
            var valueListenerClass = Class.forName(properties.getProperty(valueName));
            var constructor = valueListenerClass.getConstructor(ParameterizableInstrument.class);
            ValueListener valueListener = (ValueListener) constructor.newInstance(this);
            return valueListener;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean getValueInUse(String valueName) {
        Value value = values.get(valueName);
        if (null == value) {
            throw new NullPointerException();
        }
        return !(value instanceof NotNecessaryValue)
                || ((NotNecessaryValue) value).useValue();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String[] getParameterGroupNames() {
        String[] parameterGroupNames = new String[0];
        for (ValueListener valueListener : valueListenerList) {
            parameterGroupNames =
                    ArrayConcatenation.concatArrays(parameterGroupNames, valueListener.getParameterGroupNames());
        }
        return parameterGroupNames;
    }

    protected int getValue(String valueName) {
        return values.get(valueName).getValue();
    }

    protected void changeValue(String valueName, int mouseWheelClicks) {
        Value value = values.get(valueName);
        value.changeValue(-value.getStep() * mouseWheelClicks);
        repaint();
    }

    public abstract void repaint();

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        for (ValueListener valueListener : valueListenerList) {
            valueListener.mouseWheelMoved(e);
        }
    }

    protected abstract class ValueListener implements MouseWheelListener {

        private final int inputEventNumber;
        protected final String[] valueNames;

        protected ValueListener(int inputEventNumber, String... valueNames) {
            this.inputEventNumber = inputEventNumber;
            this.valueNames = valueNames;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (0 != (e.getModifiersEx() & inputEventNumber)) {
                for (String valueName : valueNames) {
                    changeValue(valueName, e.getWheelRotation());
                }
            }
        }

        public abstract String[] getParameterGroupNames();
    }

    protected class AngleListener extends ValueListener {
        public AngleListener() {
            super(InputEvent.CTRL_DOWN_MASK, "angle");
        }

        @Override
        public String[] getParameterGroupNames() {
            return new String[]{"rotatable"};
        }
    }

    protected class StrokeWidthListener extends ValueListener {
        public StrokeWidthListener() {
            super(InputEvent.SHIFT_DOWN_MASK, "width");
        }

        @Override
        public String[] getParameterGroupNames() {
            return new String[]{"line"};
        }
    }
}
