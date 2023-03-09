package ru.nsu.fit.icg.lab1.instrument.resizable;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class StarInstrument extends ResizableInstrument {

    public StarInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser, 3, "outerRadius", "innerRadius");
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation
                .concatArrays(super.getParameterGroupNames(), new String[]{"polygon", "star"});
    }

    @Override
    public String getName() {
        return "Звезда";
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(getValue("width")));
        int verticesAmount = getValue("verticesAmount");
        int[] x = new int[2 * verticesAmount];
        int[] y = new int[2 * verticesAmount];
        Point firstPoint = points.get(0);
        int x1 = firstPoint.getX();
        int y1 = firstPoint.getY();
        int pointInd = 1, outerRadius, innerRadius;
        if (getValueInUse("outerRadius")) {
            outerRadius = getValue("outerRadius");
        } else {
            Point point = points.get(pointInd++);
            outerRadius = (int) sqrt(pow(x1 - point.getX(), 2) + pow(y1 - point.getY(), 2));
        }
        if (getValueInUse("innerRadius")) {
            innerRadius = getValue("innerRadius");
        } else {
            Point point = points.get(pointInd);
            innerRadius = (int) sqrt(pow(x1 - point.getX(), 2) + pow(y1 - point.getY(), 2));
        }
        int angle = getValue("angle");
        for (int i = 0; i < 2 * verticesAmount; i += 2) {
            double currentAngle = PI / 2 - PI * i / verticesAmount + toRadians(angle);
            x[i] = x1 + (int) (outerRadius * sin(currentAngle));
            y[i] = y1 + (int) (outerRadius * cos(currentAngle));
        }
        for (int i = 1; i <= 2 * verticesAmount - 1; i += 2) {
            double currentAngle = PI / 2 - PI * i / verticesAmount + toRadians(angle);
            x[i] = x1 + (int) (innerRadius * sin(currentAngle));
            y[i] = y1 + (int) (innerRadius * cos(currentAngle));
        }
        endDrawing();
        g2d.drawPolygon(x, y, 2 * verticesAmount);
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        draw((Graphics2D) bufferedImage.getGraphics());
    }

    @Override
    protected String[] getResizableParams() {
        return new String[]{"outerRadius", "innerRadius"};
    }
}
