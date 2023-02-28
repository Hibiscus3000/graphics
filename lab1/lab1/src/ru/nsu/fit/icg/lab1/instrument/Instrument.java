package ru.nsu.fit.icg.lab1.instrument;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Instrument {

    private final List<AbstractButton> buttons = new ArrayList<>();

    public void setSelected() {
        for (AbstractButton abstractButton : buttons) {
            abstractButton.setSelected(true);
        }
    }

}
