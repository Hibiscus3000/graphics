package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.instrument.Instrument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PaintPanel extends JPanel implements InstrumentListener, ColorListener {

    private Instrument instrument;

    private static final int maxListSize = 50;
    private final List<BufferedImage> bufferedImages = new ArrayList<>(maxListSize);
    private BufferedImage currentBufferedImage;

    public PaintPanel() {
        super();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension preferredSize = getPreferredSize();
                setPreferredSize(
                        new Dimension(
                                (int) Math.max(getWidth(), preferredSize.getWidth()),
                                (int) Math.max(getHeight(), preferredSize.getHeight())));
                resizeBufferedImage();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (null == currentBufferedImage) {
            initFirstImage();
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(currentBufferedImage, 0, 0, this);
    }

    private void initFirstImage() {
        bufferedImages.add(currentBufferedImage);
    }

    private void resizeBufferedImage() {
        BufferedImage previousBufferedImage = currentBufferedImage;
        currentBufferedImage = null != previousBufferedImage ?
                new BufferedImage(
                        Math.max(currentBufferedImage.getWidth(), getWidth()),
                        Math.max(currentBufferedImage.getHeight(), getHeight()),
                        BufferedImage.TYPE_INT_RGB)
                : new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) currentBufferedImage.getGraphics();
        g2d.setPaint(new Color(255, 255, 255));
        g2d.fillRect(0, 0, currentBufferedImage.getWidth(), currentBufferedImage.getHeight());
        if (null != previousBufferedImage) {
            g2d.drawImage(previousBufferedImage, 0, 0, this);
        }
        repaint();
    }

    @Override
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public void setColor(Color color) {

    }
}
