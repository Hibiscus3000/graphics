package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.line.CurveLineInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class EllipseInstrument extends CurveLineInstrument {

    private Point firstPoint;
    private boolean firstPointSet = false;
    private Point secondPoint;

    private boolean semiMinorAxisSet = false;
    private int semiMinorAxis;
    private boolean semiMajorAxisSet = false;
    private int semiMajorAxis;

    public EllipseInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser);
        semiMinorAxis = defaults.get("semiMinorAxis");
        semiMajorAxis = defaults.get("semiMajorAxis");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        super.changeParameters(parameters);
        semiMinorAxis = parameters.getValue("semiMinorAxis");
        semiMinorAxisSet = parameters.isSet("semiMinorAxis");
        semiMajorAxis = parameters.getValue("semiMajorAxis");
        semiMajorAxisSet = parameters.isSet("semiMajorAxis");
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation.concatArrays(new String[]{"line", "ellipse"},
                super.getParameterGroupNames());
    }

    @Override
    public String getName() {
        return "Эллипс";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width));
        int x, y, width, height;
        if (semiMajorAxisSet) {
            width = 2 * semiMajorAxis;
            x = firstPoint.getX() - semiMajorAxis;
        } else {
            width = 2 * Math.abs(firstPoint.getX() - secondPoint.getX());
            x = secondPoint.getX() - firstPoint.getX() > 0 ? 2 * firstPoint.getX() - secondPoint.getX()
                    : secondPoint.getX();
        }
        if (semiMinorAxisSet) {
            height = 2 * semiMinorAxis;
            y = firstPoint.getY() - semiMinorAxis;
        } else {
            height = 2 * Math.abs(firstPoint.getY() - secondPoint.getY());
            y = secondPoint.getY() - firstPoint.getY() > 0 ? 2 * firstPoint.getY() - secondPoint.getY()
                    : secondPoint.getY();
        }
        g2d.drawOval(x, y, width, height);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        draw((Graphics2D) bufferedImage.getGraphics());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (semiMajorAxisSet && semiMinorAxisSet) {
            firstPoint = new Point(e.getX(), e.getY());
            instrumentUser.repaintComplete();
        } else {
            if (firstPointSet) {
                secondPoint = new Point(e.getX(), e.getY());
                instrumentUser.repaintComplete();
            } else {
                firstPoint = new Point(e.getX(), e.getY());
            }
            firstPointSet = !firstPointSet;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (firstPointSet) {
            secondPoint = new Point(e.getX(), e.getY());
            instrumentUser.repaintIncomplete();
        }
    }
}
