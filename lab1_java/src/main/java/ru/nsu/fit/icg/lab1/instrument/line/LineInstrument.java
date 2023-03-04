package ru.nsu.fit.icg.lab1.instrument.line;

import ru.nsu.fit.icg.lab1.instrument.Instrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.Parameters;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Line;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class LineInstrument extends Instrument {

    protected Line line;
    protected int width;

    public LineInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser);
        width = defaults.get("width");
    }

    @Override
    public void changeParameters(Parameters parameters) {
        width = parameters.getValue("width");
    }

    @Override
    public String[] getParameterGroupNames() {
        return new String[]{"line"};
    }

    @Override
    public void draw(Graphics2D g2d) {
        java.util.List<ru.nsu.fit.icg.lab1.line.Point> points = line.getPoints();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(width));
        for (int i = 0; i < points.size() - 1; ++i) {
            ru.nsu.fit.icg.lab1.line.Point point1 = points.get(i);
            ru.nsu.fit.icg.lab1.line.Point point2 = points.get(i + 1);
            g2d.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        }
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        List<Point> points = line.getPoints();
        if (1 == width) {
            // Bresenham
            for (int i = 0; i < points.size() - 1; ++i) {
                Point pointI = points.get(i);
                Point pointIPlusOne = points.get(i);
                int x0 = pointI.getX();
                int y0 = pointI.getY();
                int x1 = pointIPlusOne.getX();
                int y1 = pointIPlusOne.getY();
                int dx = x1 - x0;
                int dy = y1 - y0;
                int dxAbs = Math.abs(dx);
                int dyAbs = Math.abs(dy);
                if (dxAbs < dyAbs) {
                    bresenhamAlgorithmLineHigh(Math.min(x0, x1), y0,
                            dxAbs, dyAbs, 1, 1, bufferedImage);
                } else {
                    bresenhamAlgorithmLineLow(Math.min(x0, x1), y0,
                            dxAbs, dyAbs, 1, 1, bufferedImage);
                }
            }
        } else {
            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            draw(g2d);
        }
    }

    private void bresenhamAlgorithmLineLow(int x0, int y0, int dxAbs, int dyAbs,
                                           int xStep, int yStep,
                                           BufferedImage bufferedImage) {
        int err = -dxAbs;
        int x = x0, y = y0;
        for (int j = 0; j < dxAbs; ++j) {
            x += xStep;
            err += 2 * dyAbs;
            if (err > 0) {
                err -= 2 * dxAbs;
                y += yStep;
            }
            bufferedImage.setRGB(x, y, color.getRGB());
        }
    }

    private void bresenhamAlgorithmLineHigh(int x0, int y0, int dxAbs, int dyAbs,
                                            int xStep, int yStep,
                                            BufferedImage bufferedImage) {
        int err = -dyAbs;
        int x = x0, y = y0;
        for (int j = 0; j < dyAbs; ++j) {
            y += yStep;
            err += 2 * dxAbs;
            if (err > 0) {
                err -= 2 * dyAbs;
                x += xStep;
            }
            bufferedImage.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        line = new Line(new Point(e.getX(), e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

