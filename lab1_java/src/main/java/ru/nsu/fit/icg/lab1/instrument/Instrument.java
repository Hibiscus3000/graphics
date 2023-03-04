package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Instrument implements MouseListener, MouseMotionListener {

    protected final Map<String, Integer> defaults;
    protected Color color = Color.BLACK;
    protected final InstrumentUser instrumentUser;

    protected Instrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        defaults = parametersParser.getDefaults(getParameterGroupNames());
        this.instrumentUser = instrumentUser;
    }

    public abstract void changeParameters(Parameters parameters);

    public abstract String[] getParameterGroupNames();

    public abstract String getName();

    public abstract void draw(Graphics2D g2d);

    public abstract void draw(BufferedImage bufferedImage);

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
