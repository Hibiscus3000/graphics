package ru.nsu.fit.icg.lab1.instrument.resizable;

import ru.nsu.fit.icg.lab1.ArrayConcatenation;
import ru.nsu.fit.icg.lab1.instrument.ParameterizableInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static java.awt.event.MouseEvent.BUTTON1;

public abstract class ResizableInstrument extends ParameterizableInstrument {

    protected final Semaphore paintSemaphore = new Semaphore(1);

    protected List<Point> points = new ArrayList<>();
    private Point lastTempPoint;
    protected final int needPointsMax;

    private boolean clearAll;

    protected ResizableInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser,
                                  int needPointsMax, String... sizeParamNames) {
        super(parametersParser, instrumentUser, "width", "angle");
        valueListenerList.add(new SizeListener(sizeParamNames));
        this.needPointsMax = needPointsMax;
    }

    private int getNumberOfUnwantedPoints() {
        int numberOfUnwantedPoints = 0;
        for (String paramName : getResizableParams()) {
            if (getValueInUse(paramName)) {
                ++numberOfUnwantedPoints;
            }
        }
        return numberOfUnwantedPoints;
    }

    private int getNumOfPointsToPaintTemporary() {
        int numOfPointsToPaintTemp = needPointsMax;
        for (String paramName : getResizableParams()) {
            if (getValueInUse(paramName)) {
                --numOfPointsToPaintTemp;
            }
        }
        return numOfPointsToPaintTemp;
    }

    @Override
    public void repaint() {
        if (points.size() == getNumOfPointsToPaintTemporary() - 1 && null != lastTempPoint) {
            try {
                points.add(lastTempPoint);
                clearAll = false;
                paintSemaphore.acquire();
                instrumentUser.repaintTemporary();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected abstract String[] getResizableParams();

    @Override
    public void mouseReleased(MouseEvent e) {
        if (BUTTON1 != e.getButton()) {
            return;
        }
        try {
            paintSemaphore.acquire();
            int numberOfUnwantedPoints = getNumberOfUnwantedPoints();
            points.add(new Point(e.getX(), e.getY()));
            if (points.size() == needPointsMax - numberOfUnwantedPoints) {
                clearAll = true;
                instrumentUser.repaintComplete();
            } else {
                paintSemaphore.release();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (points.size() == getNumOfPointsToPaintTemporary() - 1) {
            try {
                paintSemaphore.acquire();
                points.add(lastTempPoint = new Point(e.getX(), e.getY()));
                clearAll = false;
                instrumentUser.repaintTemporary();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void endDrawing() {
        if (clearAll) {
            points.clear();
            lastTempPoint = null;
        } else {
            points.remove(points.size() - 1);
        }
        paintSemaphore.release(numberOfPermitsToRelease);
    }

    @Override
    public String[] getParameterGroupNames() {
        return ArrayConcatenation.concatArrays(new String[]{"line"},
                super.getParameterGroupNames());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        instrumentUser.repaint();
        lastTempPoint = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    protected class SizeListener extends ValueListener {

        public SizeListener(String... valueNames) {
            super(0, valueNames);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (0 != e.getModifiersEx()) {
                return;
            }
            for (String valueName : valueNames) {
                changeValue(valueName, e.getWheelRotation());
            }
        }

        @Override
        public String[] getParameterGroupNames() {
            return new String[0];
        }
    }
}
