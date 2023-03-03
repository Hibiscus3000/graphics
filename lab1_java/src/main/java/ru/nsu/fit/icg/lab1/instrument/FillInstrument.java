package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;

public class FillInstrument extends Instrument {

    public FillInstrument(ParametersParser parametersParser) {
        super(parametersParser);
    }

    @Override
    public void changeParameters(Parameters parameters) {
    }

    @Override
    public String[] getParameterGroupNames() {
        return new String[]{"fill"};
    }

    @Override
    public String getName() {
        return "Заливка";
    }
}
