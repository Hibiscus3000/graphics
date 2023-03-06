package ru.nsu.fit.icg.lab1.instrument;

import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public abstract class Instrument implements MouseListener, MouseMotionListener {

    protected final InstrumentUser instrumentUser;

    protected Instrument(InstrumentUser instrumentUser) {
        this.instrumentUser = instrumentUser;
    }

    public abstract String getName();

    public abstract void draw(Graphics2D g2d);

    public abstract void draw(BufferedImage bufferedImage);

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
