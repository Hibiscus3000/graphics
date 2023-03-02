package ru.nsu.fit.icg.lab1.toggle_button;

import ru.nsu.fit.icg.lab1.action.ExclusiveAction;
import ru.nsu.fit.icg.lab1.action.ExclusiveActionListener;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InstrumentToggleButton extends ExclusiveToggleButton{

    public InstrumentToggleButton(InstrumentAction instrumentActionAction) {
        super(instrumentActionAction);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON3 -> instrumentActionAction.changeParameters();
                }
            }
        });
    }

}
