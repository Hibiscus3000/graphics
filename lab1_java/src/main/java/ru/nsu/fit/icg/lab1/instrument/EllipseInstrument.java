package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

public class EllipseInstrument extends CurveLineInstrument {

    private int width;
    private int semiMinorAxis;
    private int semiMajorAxis;
    private int turn;

    public EllipseInstrument(ParametersParser parametersParser) {
        super(parametersParser);
        semiMinorAxis = defaults.get("semiMinorAxis");
        semiMajorAxis = defaults.get("semiMajorAxis");
        turn = defaults.get("turn");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        super.changeParameters(parameters);
        semiMinorAxis = parameters.getValue("semiMinorAxis");
        semiMajorAxis = parameters.getValue("semiMajorAxis");
        turn = parameters.getValue("turn");
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation.concatArrays(new String[]{"line", "rotating", "ellipse"},
                super.getParameterGroupNames());
    }

    @Override
    public String getName() {
        return "Эллипс";
    }
}
