package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class StarInstrument extends PolygonInstrument {

    private Point thirdPoint;
    private boolean secondPointSet = false;

    private int innerRadius;
    private boolean innerRadiusSet = false;

    public StarInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser);
        innerRadius = defaults.get("innerRadius");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        super.changeParameters(parameters);
        innerRadius = parameters.getValue("innerRadius");
        innerRadiusSet = parameters.isSet("innerRadius");
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

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width));
        int[] x = new int[2 * verticesAmount];
        int[] y = new int[2 * verticesAmount];
        int x1 = firstPoint.getX();
        int y1 = firstPoint.getY();
        int outerR = outerRadiusSet ? outerRadius : (int) sqrt(pow(x1 - thirdPoint.getX(), 2)
                + pow(y1 - thirdPoint.getY(), 2));
        int innerR = innerRadiusSet ? innerRadius : (int) sqrt(pow(x1 - secondPoint.getX(), 2)
                + pow(y1 - secondPoint.getY(), 2));
        for (int i = 0; i < 2 * verticesAmount; i += 2) {
            x[i] = x1 + (int) (outerR * sin(PI / 2 - PI * i / verticesAmount + toRadians(turn)));
            y[i] = y1 + (int) (outerR * cos(PI / 2 - PI * i / verticesAmount + toRadians(turn)));
        }
        for (int i = 1; i <= 2 * verticesAmount - 1; i += 2) {
            x[i] = x1 + (int) (innerR * sin(PI / 2 - PI * i / verticesAmount + toRadians(turn)));
            y[i] = y1 + (int) (innerR * cos(PI / 2 - PI * i / verticesAmount + toRadians(turn)));
        }
        g2d.drawPolygon(x, y, 2 * verticesAmount);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        draw((Graphics2D) bufferedImage.getGraphics());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (firstPointSet && (secondPointSet || innerRadiusSet)) {
            thirdPoint = new Point(e.getX(), e.getY());
            instrumentUser.repaintIncomplete();
        } else if (firstPointSet && outerRadiusSet) {
            secondPoint = new Point(e.getX(), e.getY());
            instrumentUser.repaintIncomplete();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (outerRadiusSet && innerRadiusSet) {
            firstPoint = new Point(e.getX(), e.getY());
            instrumentUser.repaintComplete();
        } else {
            if (firstPointSet) {
                if (innerRadiusSet) {
                    thirdPoint = new Point(e.getX(), e.getY());
                    firstPointSet = false;
                    instrumentUser.repaintComplete();
                } else if (outerRadiusSet) {
                    secondPoint = new Point(e.getX(), e.getY());
                    firstPointSet = false;
                    instrumentUser.repaintComplete();
                } else if (secondPointSet) {
                    thirdPoint = new Point(e.getX(), e.getY());
                    firstPointSet = false;
                    instrumentUser.repaintComplete();
                } else {
                    secondPoint = new Point(e.getX(), e.getY());
                }
                secondPointSet = !secondPointSet;
            } else {
                firstPoint = new Point(e.getX(), e.getY());
                firstPointSet = true;
            }
        }
    }
}
