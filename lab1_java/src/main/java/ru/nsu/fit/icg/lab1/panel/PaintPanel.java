package ru.nsu.fit.icg.lab1.panel;

import ru.nsu.fit.icg.lab1.instrument.Instrument;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaintPanel extends JPanel implements InstrumentUser, ColorListener {

    private Instrument instrument;

    private static final int maxListSize = 50;
    private final List<BufferedImage> bufferedImages = new ArrayList<>(maxListSize);
    private int currentIndex = 0;

    private boolean callInstrument = false;

    public PaintPanel() {
        super();
        bufferedImages.add(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension preferredSize = getPreferredSize();
                setPreferredSize(
                        new Dimension(
                                (int) Math.max(getWidth(), preferredSize.getWidth()),
                                (int) Math.max(getHeight(), preferredSize.getHeight())));
                createNewBufferedImage(currentIndex, false);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufferedImages.get(currentIndex), 0, 0, this);
        if (callInstrument) {
            instrument.draw(g2d);
            callInstrument = false;
        }
    }

    @Override
    public void setInstrument(Instrument instrument) {
        removeMouseListener(this.instrument);
        removeMouseMotionListener(this.instrument);
        this.instrument = instrument;
        addMouseMotionListener(instrument);
        addMouseListener(instrument);
    }

    @Override
    public void repaintComplete() {
        addNewImageToBuffer(false);
        instrument.draw(bufferedImages.get(currentIndex));
        repaint();
    }

    private void addNewImageToBuffer(boolean clear) {
        currentIndex = Math.min(currentIndex + 1, maxListSize);
        if (currentIndex == maxListSize) {
            --currentIndex;
            bufferedImages.remove(0);
        }
        createNewBufferedImage(currentIndex - 1, clear);
    }

    private void createNewBufferedImage(int index, boolean clear) {
        BufferedImage newImage = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) newImage.getGraphics();
        g2d.setPaint(new Color(255, 255, 255));
        g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        BufferedImage previousBufferedImage = 0 == currentIndex || clear ? null : bufferedImages.get(index);
        if (null != previousBufferedImage) {
            g2d.drawImage(previousBufferedImage, 0, 0, this);
        }
        bufferedImages.add(currentIndex, newImage);
    }

    @Override
    public void repaintIncomplete() {
        callInstrument = true;
        repaint();
    }

    @Override
    public void setColor(Color color) {
        if (null != instrument) {
            instrument.setColor(color);
        }
    }

    public void clear() {
        addNewImageToBuffer(true);
        repaint();
    }

    public void openImage(File file) throws IOException {
        addNewImageToBuffer(true);

        BufferedImage in = ImageIO.read(file);

        BufferedImage currentImage = bufferedImages.get(currentIndex);
        Graphics2D g2d = (Graphics2D) currentImage.getGraphics();

        g2d.drawImage(in, 0, 0, null);
        repaint();
    }

    public BufferedImage getCurrentImage() {
        return bufferedImages.get(currentIndex);
    }
}
