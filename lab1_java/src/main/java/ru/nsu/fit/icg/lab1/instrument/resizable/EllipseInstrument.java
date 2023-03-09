package ru.nsu.fit.icg.lab1.instrument.resizable;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EllipseInstrument extends ResizableInstrument {

    public EllipseInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser, 3, "semiMinorAxis", "semiMajorAxis");
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation.concatArrays(new String[]{"ellipse"},
                super.getParameterGroupNames());
    }

    @Override
    public String getName() {
        return "Эллипс";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(getValue("width")));
        int x, y, width, height;
        boolean semiMajorAxisSet = getValueInUse("semiMajorAxis");
        boolean semiMinorAxisSet = getValueInUse("semiMinorAxis");

        Point firstPoint = points.get(0);
        int pointInd = 1;
        if (semiMajorAxisSet) {
            int semiMajorAxis = getValue("semiMajorAxis");
            width = 2 * semiMajorAxis;
            x = firstPoint.getX() - semiMajorAxis;
        } else {
            Point point = points.get(pointInd++);
            width = 2 * Math.abs(firstPoint.getX() - point.getX());
            x = point.getX() - firstPoint.getX() > 0 ? 2 * firstPoint.getX() - point.getX()
                    : point.getX();
        }
        if (semiMinorAxisSet) {
            int semiMinorAxis = getValue("semiMinorAxis");
            height = 2 * semiMinorAxis;
            y = firstPoint.getY() - semiMinorAxis;
        } else {
            Point point = points.get(pointInd);
            height = 2 * Math.abs(firstPoint.getY() - point.getY());
            y = point.getY() - firstPoint.getY() > 0 ? 2 * firstPoint.getY() - point.getY()
                    : point.getY();
        }
        endDrawing();
        g2d.rotate(Math.toRadians(getValue("angle")), x + width / 2, y + height / 2);
        g2d.drawOval(x, y, width, height);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        draw((Graphics2D) bufferedImage.getGraphics());
        points.clear();
    }

    @Override
    protected String[] getResizableParams() {
        return new String[]{"semiMinorAxis", "semiMajorAxis"};
    }
}
