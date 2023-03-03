package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

public class CurveLineInstrument extends Instrument {

    private int width;

    public CurveLineInstrument(ParametersParser parametersParser) {
        super(parametersParser);
        width = defaults.get("width");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        width = parameters.getValue("width");
    }

    @Override
    public String[] getParameterGroupNames() {
        return new String[]{"line"};
    }

    @Override
    public String getName() {
        return "Кривая";
    }
}
