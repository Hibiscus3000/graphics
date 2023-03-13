package ru.nsu.fit.icg.lab1.instrument.line;

import ru.nsu.fit.icg.lab1.instrument.ParameterizableInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Line;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class LineInstrument extends ParameterizableInstrument {

    protected Line line;

    public LineInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser, "width");
    }

    @Override
    public synchronized void draw(Graphics2D g2d) {
        if (null == line) {
            return;
        }
        java.util.List<Point> points = line.getPoints();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(getValue("width")));
        for (int i = 0; i < points.size() - 1; ++i) {
            Point point1 = points.get(i);
            Point point2 = points.get(i + 1);
            g2d.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        }
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        if (null == line) {
            return;
        }
        final int rgb = color.getRGB();
        List<Point> points = line.getPoints();
        if (1 == getValue("width")) {
            // Bresenham
            for (int i = 0; i < points.size() - 1; ++i) {
                Point pointI = points.get(i);
                Point pointIPlusOne = points.get(i + 1);
                final int x0 = constrain(pointI.getX(), 0, bufferedImage.getWidth() - 1);
                final int y0 = constrain(pointI.getY(), 0, bufferedImage.getHeight() - 1);
                final int x1 = constrain(pointIPlusOne.getX(), 0, bufferedImage.getWidth() - 1);
                final int y1 = constrain(pointIPlusOne.getY(), 0, bufferedImage.getHeight() - 1);
                final int dxAbs = Math.abs(x1 - x0), dyAbs = Math.abs(y1 - y0);

                if (dxAbs > dyAbs) {
                    if (x1 > x0) {
                        bresenhamTanAbsLessOne(x0, y0, x1, y1, rgb, bufferedImage);
                    } else {
                        bresenhamTanAbsLessOne(x1, y1, x0, y0, rgb, bufferedImage);
                    }
                } else {
                    if (y1 > y0) {
                        bresenhamTanAbsMoreOne(x0, y0, x1, y1, rgb, bufferedImage);
                    } else {
                        bresenhamTanAbsMoreOne(x1, y1, x0, y0, rgb, bufferedImage);
                    }
                }
            }
        } else {
            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            draw(g2d);
        }
        line = null;
    }

    private int constrain(int value, int lowerBorder, int upperBorder) {
        return Math.min(upperBorder, Math.max(value, lowerBorder));
    }

    private void bresenhamTanAbsLessOne(int x0, int y0, int x1, int y1, int rgb,
                                        BufferedImage bufferedImage) {
        final int dy = y1 - y0;
        final int dxAbs = Math.abs(x1 - x0), dyAbs = Math.abs(dy);
        int err = -dxAbs;
        int y = y0;
        int yStep = dy > 0 ? 1 : -1;
        for (int x = x0; x < x1; ++x) {
            err += 2 * dyAbs;
            if (err > 0) {
                err -= 2 * dxAbs;
                y += yStep;
            }
            bufferedImage.setRGB(x, y, rgb);
        }
    }

    private void bresenhamTanAbsMoreOne(int x0, int y0, int x1, int y1, int rgb,
                                        BufferedImage bufferedImage) {
        final int dx = x1 - x0;
        final int dxAbs = Math.abs(dx), dyAbs = Math.abs(y1 - y0);
        int err = -dyAbs;
        int x = x0;
        int xStep = dx > 0 ? 1 : -1;
        for (int y = y0; y < y1; ++y) {
            err += 2 * dxAbs;
            if (err > 0) {
                err -= 2 * dyAbs;
                x += xStep;
            }
            bufferedImage.setRGB(x, y, rgb);
        }
    }

    @Override
    public void repaint() {
        instrumentUser.repaintTemporary();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        line = new Line(new Point(e.getX(), e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

