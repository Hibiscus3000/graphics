package ru.nsu.fit.icg.lab1.instrument.parameter;

import ru.nsu.fit.icg.lab1.PaintFrame;
import ru.nsu.fit.icg.lab1.instrument.parameter.listener.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;

public class ParametersDialog extends JDialog implements ValueErrorListener {

    private final JButton okButton;

    private final Map<String, Value> values = new HashMap<>();
    private final Map<String, Integer> previousValues = new HashMap<>();

    // errorLabel is the label that appears in parameters row and that won't disappear until the
    // error is fixed
    // warningLabel is the label that appears in the button part of the dialog
    private final JLabel warningLabel;
    private final Map<String, String> warningsMap = new HashMap<>(); // parameterName => warningMsg
    private final Map<String, JLabel> errorLabels = new HashMap<>(); // parameterName => errorLabel

    private final String errorMessage = "Неверное значение!";

    private boolean valueChanged = false;

    public ParametersDialog(PaintFrame owner, String title, Map<String, Parameter> parameters) {
        super(owner, title, true);
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

        for (Map.Entry<String, Parameter> parameterEntry : parameters.entrySet().stream().toList()) {
            values.put(parameterEntry.getKey(), parameterEntry.getValue().getValue());
        }
        Map<String, Parameter> parametersSorted = (Map<String, Parameter>) (parameters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry<String, Parameter>::getKey,
                        Map.Entry<String, Parameter>::getValue,
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap<String, Parameter>::new)));

        warningLabel = new JLabel();
        warningLabel.setForeground(Color.red);

        okButton = new JButton("ОК");
        okButton.addActionListener(e -> {
            clearValuesListeners();
            dispose();
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> cancel());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel parameterPanel = new JPanel();
        parameterPanel.setLayout(new GridBagLayout());
        int y = 0;
        for (Map.Entry<String, Parameter> parameterEntry : parametersSorted.entrySet().stream().toList()) {
            Value value = parameterEntry.getValue().getValue();
            previousValues.put(parameterEntry.getKey(), value.getValue());
            Parameter parameter = parameterEntry.getValue();

            String name = parameter.getName();
            JLabel nameLabel = new JLabel(name);

            JLabel errorLabel = new JLabel("");
            errorLabels.put(name, errorLabel);
            errorLabel.setForeground(Color.red);
            errorLabel.setPreferredSize(new Dimension(150, 50));

            parameterPanel.add(nameLabel, new GBC(0, y, 1, 1, false));

            final List<ValueListener> valueListenerList = new ArrayList<>();

            ValueTextField valueTextField = new ValueTextField(parameter, this);
            parameterPanel.add(valueTextField, new GBC(1, y, 1, 1, false));
            valueListenerList.add(valueTextField);

            ValueSlider valueSlider = new ValueSlider(parameter, this);
            parameterPanel.add(valueSlider, new GBC(2, y, 3, 1, true));
            valueListenerList.add(valueSlider);

            ValueButtonPanel valueButtonPanel = new ValueButtonPanel(parameter, this);
            parameterPanel.add(valueButtonPanel, new GBC(7, y, 1, 1, false));
            valueListenerList.add(valueButtonPanel);

            parameterPanel.add(errorLabel, new GBC(9, y, 1, 1, false));

            addValueListenersToValue(parameter.getValue(), valueListenerList);

            if (value instanceof NotNecessaryValue) {
                Checkbox checkbox = new Checkbox("Использовать со значением",
                        ((NotNecessaryValue) value).useValue());
                checkbox.addItemListener(e -> {
                    ((NotNecessaryValue) value).setUseValue(checkbox.getState());
                    setAllValueListenersEnabled(checkbox.getState(), valueListenerList);
                });
                setAllValueListenersEnabled(checkbox.getState(), valueListenerList);
                parameterPanel.add(checkbox, new GBC(8, y, 1, 1, false));
            }
            ++y;
        }

        dialogPanel.add(parameterPanel);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel warningPanel = new JPanel();
        warningPanel.setPreferredSize(new Dimension(100, 30));
        warningPanel.add(warningLabel);
        dialogPanel.add(warningPanel);
        dialogPanel.add(buttonPanel);
        add(dialogPanel);
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
    }

    private void addValueListenersToValue(Value value, List<ValueListener> valueListenerList) {
        for (ValueListener valueListener : valueListenerList) {
            value.addValueListener(valueListener);
        }
    }

    private void setWarningMsg() {
        EventQueue.invokeLater(() -> {
            warningLabel.setText(0 == warningsMap.size() ? ""
                    : warningsMap.entrySet().stream().toList().get(0).getValue());
        });
        if (0 == warningsMap.size()) {
            okButton.setEnabled(true);
        } else {
            okButton.setEnabled(false);
        }
    }

    private void setErrorMsg(JLabel label, String text) {
        EventQueue.invokeLater(() -> label.setText(text));
    }

    private void setAllValueListenersEnabled(boolean enabled, List<ValueListener> valueListenerList) {
        for (ValueListener valueListener : valueListenerList) {
            valueListener.setEnabled(enabled);
        }
    }

    @Override
    public void addError(String warningMsg, String parameterName) {
        warningsMap.put(parameterName, warningMsg);
        setErrorMsg(errorLabels.get(parameterName), errorMessage);
        setWarningMsg();
    }

    @Override
    public void removeError(String parameterName) {
        warningsMap.remove(parameterName);
        setErrorMsg(errorLabels.get(parameterName), "");
        setWarningMsg();
        valueChanged = true;
    }

    private void cancel() {
        if (!valueChanged
                || JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите отменить все внесенные изменения?",
                "Отмена",
                OK_CANCEL_OPTION)) {
            for (Map.Entry<String, Value> valueEntry : values.entrySet().stream().toList()) {
                valueEntry.getValue().setValue(previousValues.get(valueEntry.getKey()), null);
            }
            clearValuesListeners();
            dispose();
        }
    }

    private void clearValuesListeners() {
        for (Map.Entry<String, Value> valueEntry : values.entrySet().stream().toList()) {
            valueEntry.getValue().clearListeners();
        }
    }

    private class GBC extends GridBagConstraints {

        private final int inset = 10;

        public GBC(int gridX, int gridY, int gridWidth, int gridHeight, boolean stretchWidth) {
            gridx = gridX;
            gridy = gridY;
            gridwidth = gridWidth;
            gridheight = gridHeight;
            insets = new Insets(inset, inset, inset, inset);
            weightx = 100;
            weighty = 100;
            if (stretchWidth) {
                fill = HORIZONTAL;
            } else {
                fill = NONE;
                anchor = EAST;
            }
        }
    }
}