package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

import java.awt.*;
import java.util.Map;

public abstract class Instrument {

    protected final Map<String, Integer> defaults;
    protected Color color;

    protected Instrument(ParametersParser parametersParser) {
        defaults = parametersParser.getDefaults(getParameterGroupNames());
    }

    public abstract void changeParameters(Parameters parameters);

    public abstract String[] getParameterGroupNames();

    public abstract String getName();

    public void setColor(Color color) {
        this.color = color;
    }
}
