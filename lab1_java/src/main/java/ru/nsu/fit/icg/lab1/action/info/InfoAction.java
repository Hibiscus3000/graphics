package ru.nsu.fit.icg.lab1.action.info;

import ru.nsu.fit.icg.lab1.PaintFrame;
import ru.nsu.fit.icg.lab1.action.CommandAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InfoAction extends CommandAction {

    private final String textFileName;
    private final PaintFrame owner;

    public InfoAction(String name, String iconFileName, String shortDescription,
                      String textFileName, PaintFrame owner) {
        super(name, iconFileName, shortDescription);
        this.textFileName = textFileName;
        this.owner = owner;
    }

    //private InfoDialog infoDialog = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JOptionPane.showMessageDialog(owner,
                    new String(InfoAction.this.getClass()
                            .getResourceAsStream(textFileName).readAllBytes(),
                            StandardCharsets.UTF_8), (String) getValue(NAME),
                    JOptionPane.INFORMATION_MESSAGE);
            /*if (null == infoDialog) {
                infoDialog = new InfoDialog();
            }
            infoDialog.pack();
            infoDialog.setLocationRelativeTo(owner);
            infoDialog.setVisible(true);*/
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(owner,
                    "Не получилось прочитать файл с текстом данного диалога.\n"
                            + ex.getMessage(), "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }

    }

    /*private class InfoDialog extends JDialog {
        public InfoDialog() throws IOException {
            super(owner, (String) InfoAction.this.getValue(NAME),true);
            JTextArea textArea = new JTextArea(30,60);
            textArea.setText(new String(InfoAction.this.getClass()
                    .getResourceAsStream(textFileName).readAllBytes(),
                    StandardCharsets.UTF_8));
            textArea.setEditable(false);
            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
            //textArea.setPreferredSize(new Dimension(300, 500));
            JPanel textPanel = new JPanel();
            textPanel.add(new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
            dialogPanel.add(textPanel);
            JButton okButton = new JButton("ОК");
            okButton.addActionListener(e -> setVisible(false));
            add(dialogPanel);
        }
    }*/
}
