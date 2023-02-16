package ru.nsu.fit.graphics.exercise1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.MouseEvent.*;

public class MyPanel extends JPanel {

    private final List<Line> lines = new ArrayList<>();
    private boolean diagonalLineVisible = true;

    private boolean startCoordinatesGiven = false;
    private int startX;
    private int startY;

    private boolean continuesDrawing = false;

    private int currentX;
    private int currentY;

    private Color lineColor = Color.BLACK;
    private float width = 1;

    public MyPanel() {
        super();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()) {
                    case BUTTON3 -> {
                        if (!continuesDrawing) {
                            startCoordinatesGiven = false;
                            repaint();
                        }
                    }
                    case BUTTON1 -> {
                        if (startCoordinatesGiven) {
                            addLine(startX, startY, e.getX(), e.getY());
                            repaint();
                        } else {
                            startX = e.getX();
                            startY = e.getY();
                        }
                        startCoordinatesGiven = !startCoordinatesGiven;
                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!startCoordinatesGiven) {
                    return;
                }
                int x = e.getX();
                int y = e.getY();
                if (continuesDrawing && startX != x && startY != y) {
                    addLine(startX, startY, x, y);
                    startX = x;
                    startY = y;
                }
                currentX = x;
                currentY = y;
                repaint();
            }
        });
    }

    public void clear() {
        lines.clear();
        repaint();
    }

    public void changeDiagonalLineVisibility() {
        diagonalLineVisible = !diagonalLineVisible;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (diagonalLineVisible) {
            g2D.setStroke(new BasicStroke(1));
            g2D.setColor(Color.BLACK);
            g2D.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
        }
        for (Line line : lines) {
            g2D.setStroke(new BasicStroke(line.getWidth()));
            g2D.setColor(line.getColor());
            drawLine(line, g2D);
        }
        if (startCoordinatesGiven) {
            g2D.setStroke(new BasicStroke(width));
            g2D.setColor(lineColor);
            drawLine(startX, startY, currentX, currentY, g2D);
        }
    }

    private void drawLine(Line line, Graphics2D g2D) {
        g2D.drawLine((int) (line.getStartXScale() * getWidth()),
                (int) (line.getStartYScale() * getHeight()),
                (int) (line.getEndXScale() * getWidth()),
                (int) (line.getEndYScale() * getHeight()));
    }

    private void drawLine(int startX, int startY, int currentX, int currentY, Graphics2D g2D) {
        g2D.drawLine(startX, startY, currentX, currentY);
    }

    private void addLine(int startX, int startY, int endX, int endY) {
        lines.add(new Line(((double) startX) / getWidth(), ((double) startY) / getHeight(),
                ((double) endX) / getWidth(), ((double) endY) / getHeight(), width, lineColor));
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setAllLinesWidth() {
        for (Line line : lines) {
            line.setWidth(width);
        }
        repaint();
    }

    public void setContinuesDrawing(boolean b) {
        continuesDrawing = b;
    }

    public void setLineColor(Color color) {
        lineColor = color;
        repaint();
    }

    public void setAllLinesColor() {
        for (Line line : lines) {
            line.setColor(lineColor);
        }
        repaint();
    }

    public void undo() {
        int size = lines.size();
        if (0 != size) {
            lines.remove(size - 1);
            repaint();
        }
    }
}
