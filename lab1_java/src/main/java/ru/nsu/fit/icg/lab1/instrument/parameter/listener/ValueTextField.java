package ru.nsu.fit.icg.lab1.instrument.parameter.listener;

import ru.nsu.fit.icg.lab1.instrument.parameter.Parameter;
import ru.nsu.fit.icg.lab1.instrument.parameter.Value;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ValueTextField extends JTextField implements ValueListener {

    private int newValue;
    private boolean noCheck = false;

    public ValueTextField(Parameter parameter, ValueErrorListener valueErrorListener) {
        super(5);
        Value value = parameter.getValue();
        setText(String.valueOf(value.getValue()));
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textChanged();
            }

            private void textChanged() {
                try {
                    String text = getText();
                    if (noCheck) {
                        noCheck = false;
                        return;
                    }
                    if (!value.setValue(Integer.parseInt(text), ValueTextField.this)) {
                        addError();
                    } else {
                        valueErrorListener.removeError(parameter.getName());
                    }
                } catch (NumberFormatException e) {
                    addError();
                }
            }

            private void addError() {
                String warningMsg = String.format("Значение параметра \"%s\" должно быть целым числом между %d и %d",
                        parameter.getName(), parameter.getMin(), parameter.getMax());
                valueErrorListener.addError(warningMsg, parameter.getName());
            }
        });
    }

    @Override
    public void setValue(int value) {
        this.newValue = value;
        EventQueue.invokeLater(() -> {
            noCheck = true;
            setText(String.valueOf(newValue));
        });
    }
}
