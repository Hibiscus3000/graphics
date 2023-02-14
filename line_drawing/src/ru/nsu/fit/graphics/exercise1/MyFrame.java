package ru.nsu.fit.graphics.exercise1;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
        myFrame.setVisible(true);
    }

    public MyFrame() {
        super();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(new Dimension((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyPanel myPanel = new MyPanel();
        add(myPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(e -> myPanel.clear());
        JButton centerLineButton = new JButton("center line");
        centerLineButton.addActionListener(e -> myPanel.changeDiagonalLineVisibility());
        buttonPanel.add(clearButton);
        buttonPanel.add(centerLineButton);
        add(buttonPanel, BorderLayout.SOUTH);

    }
}