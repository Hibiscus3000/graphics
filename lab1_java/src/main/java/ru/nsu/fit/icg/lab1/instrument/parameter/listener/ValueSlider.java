package ru.nsu.fit.icg.lab1.instrument.parameter.listener;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameter;

import javax.swing.*;
import java.awt.*;

public class ValueSlider extends JSlider implements ValueListener {

    public ValueSlider(Parameter parameter, ValueErrorListener valueErrorListener) {
        super(parameter.getMin(), parameter.getMax(), parameter.getValue().getValue());
        setMinorTickSpacing(parameter.getMinorTicks());
        setMajorTickSpacing(parameter.getMajorTicks());
        setPaintTicks(true);
        setPaintLabels(true);
        setPreferredSize(new Dimension(350, 50));

        addChangeListener(e -> {
            parameter.getValue().setValue(getValue(), this);
            valueErrorListener.removeError(parameter.getName());
        });
    }
}
