package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

public class StarInstrument extends PolygonInstrument {

    private int innerRadius;

    public StarInstrument(ParametersParser parametersParser) {
        super(parametersParser);
        innerRadius = defaults.get("innerRadius");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        super.changeParameters(parameters);
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation
                .concatArrays(super.getParameterGroupNames(), new String[]{"star"});
    }

    @Override
    public String getName() {
        return "Звезда";
    }
}
