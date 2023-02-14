package ru.nsu.fit.graphics.exercise1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    private final MyPanel myPanel;

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

        myPanel = new MyPanel();
        add(myPanel, BorderLayout.CENTER);

        addButtons();
        addMenus();
    }

    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("clear");
        clearButton.addActionListener(e -> myPanel.clear());
        JButton centerLineButton = new JButton("diagonal line");
        centerLineButton.addActionListener(e -> myPanel.changeDiagonalLineVisibility());
        buttonPanel.add(clearButton);
        buttonPanel.add(centerLineButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addMenus() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu shapeMenu = new JMenu("Shape");
        menuBar.add(shapeMenu);
        JMenuItem straightItem = new JMenuItem("straight");
        straightItem.addActionListener(e -> myPanel.setContinuesDrawing(false));
        JMenuItem curveItem = new JMenuItem("curve");
        curveItem.addActionListener(e -> myPanel.setContinuesDrawing(true));
        shapeMenu.add(straightItem);
        shapeMenu.add(curveItem);

        JMenu strokeMenu = new JMenu("Stroke");
        menuBar.add(strokeMenu);
        JMenu widthMenu = new JMenu("width");
        strokeMenu.add(widthMenu);
        strokeMenu.addSeparator();
        for (int i = 1; i < 11; ++i) {
            widthMenu.add(new StrokeWidthMenuItem(i));
        }
        JMenuItem applyToAllItem = new JMenuItem("Apply to all");
        strokeMenu.add(applyToAllItem);
        applyToAllItem.addActionListener(e -> myPanel.changeAllLinesWidth());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        myPanel.setWidth(((StrokeWidthMenuItem) e.getSource()).getStrokeWidth());
    }

    private class StrokeWidthMenuItem extends JMenuItem {

        private final float strokeWidth;

        public StrokeWidthMenuItem(float strokeWidth) {
            super();
            setText(String.valueOf(strokeWidth));
            this.strokeWidth = strokeWidth;
            addActionListener(MyFrame.this);
        }

        public float getStrokeWidth() {
            return strokeWidth;
        }
    }
}