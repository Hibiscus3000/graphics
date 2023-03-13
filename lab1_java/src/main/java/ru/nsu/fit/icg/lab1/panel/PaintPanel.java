package ru.nsu.fit.icg.lab1.panel;

import ru.nsu.fit.icg.lab1.instrument.ColoredInstrument;
import ru.nsu.fit.icg.lab1.instrument.Instrument;
import ru.nsu.fit.icg.lab1.instrument.ParameterizableInstrument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaintPanel extends JPanel implements InstrumentUser {

    private Instrument instrument;

    private static final int maxListSize = 50;
    private final List<BufferedImage> bufferedImages = new ArrayList<>(maxListSize);
    private int currentIndex = -1;

    private boolean callInstrument = false;
    private boolean afterRedoUndo = false;

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
                BufferedImage resizedImage = createNewBufferedImage();
                if (-1 != currentIndex) {
                    drawImage(resizedImage, bufferedImages.get(currentIndex));
                    --currentIndex;
                }
                addNewImageToBuffer(resizedImage);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (-1 != currentIndex) {
            g2d.drawImage(bufferedImages.get(currentIndex), 0, 0, this);
        }
        if (callInstrument) {
            instrument.draw(g2d);
            callInstrument = false;
        } else if (afterRedoUndo && instrument instanceof ParameterizableInstrument) {
            ((ParameterizableInstrument) instrument).repaint();
            afterRedoUndo = false;
        }
    }

    public void setInstrument(Instrument instrument) {
        removeMouseListener(this.instrument);
        removeMouseMotionListener(this.instrument);
        if (this.instrument instanceof MouseWheelListener) {
            removeMouseWheelListener((MouseWheelListener) this.instrument);
        }
        this.instrument = instrument;
        if (null != instrument) {
            addMouseListener(instrument);
            addMouseMotionListener(instrument);
            if (this.instrument instanceof MouseWheelListener) {
                addMouseWheelListener((MouseWheelListener) instrument);
            }
        }
        setCursor(null == instrument ? Cursor.getDefaultCursor() : instrument.getCursor());
    }

    private BufferedImage createNewBufferedImage() {
        Dimension preferredSize = getPreferredSize();
        BufferedImage newImage = new BufferedImage((int) preferredSize.getWidth(),
                (int) preferredSize.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) newImage.getGraphics();
        g2d.setPaint(new Color(255, 255, 255));
        g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        return newImage;
    }

    private void addNewImageToBuffer(BufferedImage bufferedImage) {
        currentIndex = Math.min(currentIndex + 1, maxListSize);
        if (currentIndex == maxListSize) {
            --currentIndex;
            bufferedImages.remove(0);
        }
        if (currentIndex < bufferedImages.size()) {
            bufferedImages.subList(currentIndex, bufferedImages.size()).clear();
        }
        bufferedImages.add(bufferedImage);
    }

    private BufferedImage drawImage(BufferedImage target, BufferedImage origin) {
        Graphics2D g2d = (Graphics2D) target.getGraphics();
        g2d.drawImage(origin, 0, 0, this);
        return target;
    }

    @Override
    public void repaintTemporary() {
        callInstrument = true;
        repaint();
    }

    @Override
    public void repaintComplete() {
        addNewImageToBuffer(drawImage(createNewBufferedImage(), bufferedImages.get(currentIndex)));
        instrument.draw(bufferedImages.get(currentIndex));
        repaint();
    }

    public void setColor(Color color) {
        if (null != instrument && instrument instanceof ColoredInstrument) {
            ((ColoredInstrument) instrument).setColor(color);
        }
    }

    public void openImage(BufferedImage opened) throws IOException {
        Dimension preferredSize = getPreferredSize();
        setPreferredSize(new Dimension((int) Math.max(preferredSize.getWidth(), opened.getWidth()),
                (int) Math.max(preferredSize.getHeight(), opened.getHeight())));
        addNewImageToBuffer(drawImage(createNewBufferedImage(), opened));
        repaint();
    }

    public BufferedImage getCurrentImage() {
        return bufferedImages.get(currentIndex);
    }

    public void clear() {
        addNewImageToBuffer(createNewBufferedImage());
        repaint();
    }

    public void undo() {
        currentIndex = Math.max(0, currentIndex - 1);
        bufferedImages.add(currentIndex,
                drawImage(createNewBufferedImage(), bufferedImages.remove(currentIndex)));
        afterRedoUndo = true;
        repaint();
    }

    public void redo() {
        currentIndex = Math.min(bufferedImages.size() - 1, currentIndex + 1);
        afterRedoUndo = true;
        repaint();
    }
}
