package ru.nsu.fit.icg.lab1.instrument.parameter;

import ru.nsu.fit.icg.lab1.PaintFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ParametersDialog extends JDialog {

    private final Map<String, Value> values = new HashMap<>();
    private final Map<String, Integer> previousValues = new HashMap<>();

    private final JLabel warningLabel;
    private final Map<String, String> warningsMap = new HashMap<>();
    private final Map<String, JLabel> warningLabels = new HashMap<>();

    public ParametersDialog(PaintFrame owner, String title, Map<String, Parameter> parameters) {
        super(owner, title, true);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        for (Map.Entry<String, Parameter> parameterEntry : parameters.entrySet().stream().toList()) {
            values.put(parameterEntry.getKey(), parameterEntry.getValue().getValue());
        }

        warningLabel = new JLabel();
        warningLabel.setForeground(Color.red);

        JButton okButton = new JButton("ОК");
        okButton.addActionListener(e -> {
            for (Map.Entry<String, Value> valueEntry : values.entrySet().stream().toList()) {
                previousValues.put(valueEntry.getKey(), valueEntry.getValue().getValue());
            }
            setVisible(false);
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> {
            warningsMap.clear();
            setWarningMsg();
            for (Map.Entry<String, Value> valueEntry : values.entrySet().stream().toList()) {
                previousValues.put(valueEntry.getKey(), valueEntry.getValue().getValue());
            }
            for (Map.Entry<String, JLabel> warninglabelEntry : warningLabels.entrySet().stream().toList()) {
                warninglabelEntry.getValue().setVisible(false);
            }
            setVisible(false);
        });

        JPanel parameterPanel = new JPanel();
        parameterPanel.setLayout(new GridBagLayout());
        int y = 0;
        boolean atLeastOnePDoesNotReqVal = false;
        for (Map.Entry<String, Parameter> parameterEntry : parameters.entrySet().stream().toList()) {
            Value value = parameterEntry.getValue().getValue();
            previousValues.put(parameterEntry.getKey(), value.getValue());
            Parameter parameter = parameterEntry.getValue();

            String name = parameter.getName();
            JLabel label = new JLabel(name);

            JSlider slider = new JSlider(parameter.getMin(), parameter.getMax(),
                    value.getValue());
            slider.setMinorTickSpacing(parameter.getMinorTicks());
            slider.setMajorTickSpacing(parameter.getMajorTicks());
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            JLabel errorLabel = new JLabel("Неверное значение!");
            errorLabel.setVisible(false);
            warningLabels.put(name, errorLabel);

            errorLabel.setForeground(Color.red);
            parameterPanel.add(errorLabel);

            JTextField textField = new JTextField(5);
            textField.setText(String.valueOf(value.getValue()));
            textField.getDocument().addDocumentListener(new DocumentListener() {
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

                public void textChanged() {
                    if (!value.setValue(textField.getText())) {
                        String warningMsg = String.format("Значение параметра \"%s\" должно быть целым числом между %d и %d",
                                name, parameter.getMin(), parameter.getMax());
                        warningsMap.put(name, warningMsg);
                        setWarningMsg();
                        errorLabel.setVisible(true);
                        okButton.setEnabled(false);
                    } else {
                        slider.setValue(value.getValue());
                        warningsMap.remove(name);
                        setWarningMsg();
                        errorLabel.setVisible(false);
                        okButton.setEnabled(true);
                    }
                }
            });

            slider.addChangeListener(e -> {
                parameter.setValue(slider.getValue());
                EventQueue.invokeLater(() -> textField.setText(String.valueOf(value.getValue())));
                warningsMap.remove(name);
                errorLabel.setVisible(false);
                okButton.setEnabled(true);
            });
            parameterPanel.add(label, new GBC(0, y, 1, 1));
            parameterPanel.add(textField, new GBC(1, y, 1, 1));
            parameterPanel.add(slider, new GBC(2, y, 2, 1));
            parameterPanel.add(errorLabel, new GBC(5, y, 1, 1));
            if (value instanceof NotNecessaryValue) {
                atLeastOnePDoesNotReqVal = true;
                Checkbox checkbox = new Checkbox("Использовать со значением",
                        ((NotNecessaryValue) value).useValue());
                checkbox.addItemListener(e -> {
                    ((NotNecessaryValue) value).setUseValue(checkbox.getState());
                    textField.setEditable(checkbox.getState());
                    slider.setEnabled(checkbox.getState());
                });
                textField.setEditable(checkbox.getState());
                slider.setEnabled(checkbox.getState());
                parameterPanel.add(checkbox, new GBC(4, y, 1, 1));
            }
            ++y;
        }

        dialogPanel.add(parameterPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialogPanel.add(warningLabel);
        dialogPanel.add(buttonPanel);
        add(dialogPanel);
        setMinimumSize(new Dimension(atLeastOnePDoesNotReqVal ? 900 : 500, 350));
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void setWarningMsg() {
        EventQueue.invokeLater(() -> {
            warningLabel.setText(0 == warningsMap.size() ? ""
                    : warningsMap.entrySet().stream().toList().get(0).getValue());
            pack();
            setLocationRelativeTo(getOwner());
        });
    }

    private class GBC extends GridBagConstraints {

        public GBC(int gridX, int gridY, int gridWidth, int gridHeight) {
            gridx = gridX;
            gridy = gridY;
            gridwidth = gridWidth;
            gridheight = gridHeight;
            insets = new Insets(10, 10, 10, 10);
            fill = NONE;
            anchor = WEST;
        }
    }
}
