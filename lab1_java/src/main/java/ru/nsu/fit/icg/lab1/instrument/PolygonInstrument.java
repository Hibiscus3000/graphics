package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class PolygonInstrument extends ResizableInstrument {

    public PolygonInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser, 2, "outerRadius");
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation.concatArrays(new String[]{"polygon"},
                super.getParameterGroupNames());
    }

    @Override
    public String getName() {
        return "Многоугольник";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(getValue("width")));
        int verticesAmount = getValue("verticesAmount");
        int[] x = new int[verticesAmount];
        int[] y = new int[verticesAmount];
        Point firstPoint = points.get(0);
        int x1 = firstPoint.getX();
        int y1 = firstPoint.getY();
        int r;
        if (getValueInUse("outerRadius")) {
            r = getValue("outerRadius");
        } else {
            Point secondPoint = points.get(1);
            r = (int) sqrt(pow(x1 - secondPoint.getX(), 2) + pow(y1 - secondPoint.getY(), 2));
        }
        int angle = getValue("angle");
        for (int i = 0; i < verticesAmount; ++i) {
            double currentAngle = PI / 2 - 2 * PI * i / verticesAmount + toRadians(angle);
            x[i] = x1 + (int) (r * sin(currentAngle));
            y[i] = y1 + (int) (r * cos(currentAngle));
        }
        endDrawing();
        g2d.drawPolygon(x, y, verticesAmount);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        draw((Graphics2D) bufferedImage.getGraphics());
    }

    @Override
    protected String[] getResizableParams() {
        return new String[]{"outerRadius"};
    }
}
