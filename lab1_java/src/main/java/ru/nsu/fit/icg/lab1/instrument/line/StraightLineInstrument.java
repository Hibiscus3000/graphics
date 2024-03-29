package ru.nsu.fit.icg.lab1.instrument.line;

import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.line.Point;
import ru.nsu.fit.icg.lab1.panel.InstrumentUser;

import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.BUTTON1;

public class StraightLineInstrument extends LineInstrument {

    public StraightLineInstrument(ParametersParser parametersParser, InstrumentUser instrumentUser) {
        super(parametersParser, instrumentUser);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (BUTTON1 != e.getButton()) {
            return;
        }
        line.setSecondPoint(new Point(e.getX(), e.getY()));
        instrumentUser.repaintComplete();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        line.setSecondPoint(new Point(e.getX(), e.getY()));
        instrumentUser.repaintTemporary();
    }

    @Override
    public String getName() {
        return "Прямая";
    }
}