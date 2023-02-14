package ru.nsu.fit.graphics.exercise1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel implements MouseListener, MouseMotionListener {

    private final List<Line> lines = new ArrayList<>();
    private boolean diagonalLineVisible = true;

    private boolean startCoordinatesGiven = false;
    private int startX;
    private int startY;

    private boolean continuesDrawing = false;

    private int currentX;
    private int currentY;

    private float width = 1;

    public MyPanel() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
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
        for (Line line : lines) {
            g2D.setStroke(new BasicStroke(line.getWidth()));
            drawLine(line, g2D);
        }
        if (diagonalLineVisible) {
            g2D.setStroke(new BasicStroke(1));
            g2D.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (startCoordinatesGiven) {
            g2D.setStroke(new BasicStroke(width));
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
                ((double) endX) / getWidth(), ((double) endY) / getHeight(), width));
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void changeAllLinesWidth() {
        for (Line line : lines) {
            line.setWidth(width);
        }
        repaint();
    }

    public void setContinuesDrawing(boolean b) {
        continuesDrawing = b;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (startCoordinatesGiven) {
            addLine(startX, startY, e.getX(), e.getY());
            System.out.println("Line drawn");
            repaint();
        } else {
            startX = e.getX();
            startY = e.getY();
            System.out.println(startX + " " + startY);
        }
        startCoordinatesGiven = !startCoordinatesGiven;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

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
}
