package ru.nsu.fit.icg.lab1.instrument.parameter.listener;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ValueButtonPanel extends JPanel implements ValueListener {

    private List<ValueButton> valueButtonList = new ArrayList<>();
    private final Dimension smallButtonSize = new Dimension(60, 20);
    private final Dimension bigButtonSize = new Dimension(60, 30);

    public ValueButtonPanel(Parameter parameter, ValueErrorListener valueErrorListener) {
        ActionListener actionListener = e -> {
            ValueButton valueButton = (ValueButton) (e.getSource());
            parameter.getValue().changeValue(valueButton.getChangeAmount());
            valueErrorListener.removeError(parameter.getName());
        };

        valueButtonList.add(new ValueButton(-parameter.getMinorTicks(), actionListener, true));
        valueButtonList.add(new ValueButton(parameter.getMinorTicks(), actionListener, true));
        valueButtonList.add(new ValueButton(-parameter.getMajorTicks(), actionListener, false));
        valueButtonList.add(new ValueButton(parameter.getMajorTicks(), actionListener, false));

        for (ValueButton valueButton : valueButtonList) {
            add(valueButton);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        for (ValueButton valueButton : valueButtonList) {
            valueButton.setEnabled(enabled);
        }
    }

    @Override
    public void setValue(int value) {
    }

    private class ValueButton extends JButton {

        private final int changeAmount;

        public ValueButton(int changeAmount, ActionListener actionListener, boolean small) {
            super((changeAmount > 0 ? "+" : "") + changeAmount);
            if (small) {
                setPreferredSize(smallButtonSize);
            } else {
                setPreferredSize(bigButtonSize);
            }
            this.changeAmount = changeAmount;
            addActionListener(actionListener);
        }

        public int getChangeAmount() {
            return changeAmount;
        }
    }
}