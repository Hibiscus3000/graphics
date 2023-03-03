package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

public class PolygonInstrument extends StraightLineInstrument {

    protected int verticesAmount;
    protected int outerRadius;
    protected int turn;

    public PolygonInstrument(ParametersParser parametersParser) {
        super(parametersParser);
        verticesAmount = defaults.get("verticesAmount");
        outerRadius = defaults.get("outerRadius");
        turn = defaults.get("turn");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        super.changeParameters(parameters);
        verticesAmount = parameters.getValue("verticesAmount");
        outerRadius = parameters.getValue("outerRadius");
        turn = parameters.getValue("turn");
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation.concatArrays(new String[]{"polygon", "rotating"},
                super.getParameterGroupNames());
    }

    @Override
    public String getName() {
        return "Многоугольник";
    }
}