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

    public MyPanel() {
        super();
        addMouseListener(this);
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
            g2D.drawLine(line.getStartXScale(), line.getStartYScale(), line.getEndXScale(), line.getEndYScale());
        }
        if (diagonalLineVisible) {
            g2D.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (startCoordinatesGiven) {
            lines.add(new Line(((double)startX) / getWidth(), startY, e.getX(), e.getY()));
            repaint();
        } else {
            startX = e.getX();
            startY = e.getY();
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

    }
}
