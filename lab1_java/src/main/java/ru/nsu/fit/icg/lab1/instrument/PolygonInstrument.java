package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.line.StraightLineInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class PolygonInstrument extends StraightLineInstrument {

    protected Point firstPoint;
    protected boolean firstPointSet;
    protected Point secondPoint;

    protected int verticesAmount;
    protected int outerRadius;
    protected boolean outerRadiusSet = false;
    protected int turn;

    public PolygonInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser);
        verticesAmount = defaults.get("verticesAmount");
        outerRadius = defaults.get("outerRadius");
        turn = defaults.get("turn");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        super.changeParameters(parameters);
        verticesAmount = parameters.getValue("verticesAmount");
        outerRadius = parameters.getValue("outerRadius");
        outerRadiusSet = parameters.isSet("outerRadius");
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

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width));
        int[] x = new int[verticesAmount];
        int[] y = new int[verticesAmount];
        int x1 = firstPoint.getX();
        int y1 = firstPoint.getY();
        int r = outerRadiusSet ? outerRadius : (int) sqrt(pow(x1 - secondPoint.getX(), 2)
                + pow(y1 - secondPoint.getY(), 2));
        for (int i = 0; i < verticesAmount; ++i) {
            x[i] = x1 + (int) (r * sin(PI / 2 - 2 * PI * i / verticesAmount + toRadians(turn)));
            y[i] = y1 + (int) (r * cos(PI / 2 - 2 * PI * i / verticesAmount + toRadians(turn)));
        }
        g2d.drawPolygon(x, y, verticesAmount);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        draw((Graphics2D) bufferedImage.getGraphics());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (firstPointSet) {
            secondPoint = new Point(e.getX(), e.getY());
            instrumentUser.repaintIncomplete();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (outerRadiusSet) {
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
}
