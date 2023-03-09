package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import static java.awt.event.MouseEvent.BUTTON1;

public class FillInstrument extends ColoredInstrument {

    private int startX;
    private int startY;

    public FillInstrument(InstrumentUser instrumentUser) {
        super(instrumentUser);
    }

    @Override
    public String getName() {
        return "Заливка";
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    private class Span {

        private int xLeft;
        private int xRight;
        private final int y;

        public Span(int x, int y) {
            this.xLeft = x;
            this.xRight = x;
            this.y = y;
        }

        public void xStep(int step) {
            if (-1 == step) {
                --xLeft;
            } else {
                ++xRight;
            }
        }

        public int getXLeft() {
            return xLeft;
        }

        public int getXRight() {
            return xRight;
        }

        public int getY() {
            return y;
        }
    }

    @Override
    public void draw(BufferedImage bufferedImage) {
        int oldRgb = bufferedImage.getRGB(startX, startY);
        int newRgb = color.getRGB();
        if (oldRgb == newRgb) {
            return;
        }
        Stack<Span> stack = new Stack<>();
        locateNewSpan(startX, startX, startY, bufferedImage, stack, oldRgb);
        spanAlgorithm(bufferedImage, stack, oldRgb, newRgb);
    }

    public void draw(BufferedImage bufferedImage, int x, int y) {
        startX = x;
        startY = y;
        draw(bufferedImage);
    }

    private void locateNewSpan(int xLeft, int xRight, int y, BufferedImage bufferedImage, Stack<Span> stack,
                               int oldRgb) {
        Span span = null;
        for (int x = xLeft; x <= xRight; ++x) {
            if (oldRgb == bufferedImage.getRGB(x, y)) {
                if (null == span) {
                    span = new Span(x, y);
                } else {
                    span.xStep(1);
                }
                if (x == xLeft) {
                    move(x, y, -1, bufferedImage, span, oldRgb);
                }
                if (x == xRight) {
                    move(x, y, 1, bufferedImage, span, oldRgb);
                }
            } else if (null != span) {
                stack.push(span);
                span = null;
            }
        }
        if (null != span) {
            stack.push(span);
        }
    }

    private void move(int x, int y, int xStep, BufferedImage bufferedImage, Span span, int oldRgb) {
        while (((x >= 1 && -1 == xStep) || (x < bufferedImage.getWidth() - 1 && 1 == xStep))
                && oldRgb == bufferedImage.getRGB(x + xStep, y)) {
            span.xStep(xStep);
            x += xStep;
        }
    }

    private void spanAlgorithm(BufferedImage bufferedImage, Stack<Span> stack, int oldRgb, int newRgb) {
        while (0 != stack.size()) {
            Span span = stack.pop();
            int y = span.getY();
            int xLeft = span.getXLeft();
            int xRight = span.getXRight();
            for (int x = xLeft; x <= xRight; ++x) {
                bufferedImage.setRGB(x, y, newRgb);
            }
            if (y < bufferedImage.getHeight() - 1) {
                locateNewSpan(xLeft, xRight, y + 1, bufferedImage, stack, oldRgb);
            }
            if (y > 0) {
                locateNewSpan(xLeft, xRight, y - 1, bufferedImage, stack, oldRgb);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (BUTTON1 != e.getButton()) {
            return;
        }
        startX = e.getX();
        startY = e.getY();
        instrumentUser.repaintComplete();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
