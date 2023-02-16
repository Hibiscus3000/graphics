package ru.nsu.fit.graphics.exercise1;

import ru.nsu.fit.graphics.exercise1.action.BackgroundColorAction;
import ru.nsu.fit.graphics.exercise1.action.LineColorAction;
import ru.nsu.fit.graphics.exercise1.action.UndoAction;

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
        setMinimumSize(new Dimension((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        myPanel = new MyPanel();
        add(myPanel, BorderLayout.CENTER);

        UndoAction undoAction = new UndoAction(myPanel);

        addButtons();
        addMenus(undoAction);

        String undoKey = "panel.undo";
        myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl Z"),
                undoKey);
        myPanel.getActionMap().put(undoKey, undoAction);

        pack();
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

    private void addMenus(UndoAction undoAction) {
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
        JMenuItem widthApplyToAllItem = new JMenuItem("apply to all");
        widthApplyToAllItem.addActionListener(e -> myPanel.setAllLinesWidth());
        strokeMenu.add(widthApplyToAllItem);

        JMenu lineColorMenu = new JMenu("Line");
        menuBar.add(lineColorMenu);
        JMenu lineColorChooseMenu = new JMenu("color");
        lineColorMenu.add(lineColorChooseMenu);
        JMenu backgroundColorMenu = new JMenu("Background");
        menuBar.add(backgroundColorMenu);
        addColor("red", "red.png", "ctrl R", Color.RED,
                lineColorChooseMenu, backgroundColorMenu);
        addColor("green", "green.png", "ctrl G", Color.GREEN,
                lineColorChooseMenu, backgroundColorMenu);
        addColor("blue", "blue.png", "ctrl B", Color.BLUE,
                lineColorChooseMenu, backgroundColorMenu);
        addColor("white", "white.png", "ctrl W", Color.WHITE,
                lineColorChooseMenu, backgroundColorMenu);
        addColor("black", "black.png", "ctrl K", Color.BLACK,
                lineColorChooseMenu, backgroundColorMenu);

        lineColorMenu.addSeparator();
        JMenuItem lineColorApplyToAllItem = new JMenuItem("apply to all");
        lineColorApplyToAllItem.addActionListener(e -> myPanel.setAllLinesColor());
        lineColorMenu.add(lineColorApplyToAllItem);

        menuBar.add(new JMenuItem(undoAction));
    }

    private void addColor(String colorName, String iconFileName, String keyStroke, Color color,
                          JMenu lineColorChooseMenu, JMenu backgroundColorChooseMenu) {
        LineColorAction lineColorAction = new LineColorAction(myPanel, colorName, iconFileName, color);
        lineColorChooseMenu.add(lineColorAction);
        String key = "panel." + colorName;
        myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyStroke),
                key);
        myPanel.getActionMap().put(key, lineColorAction);
        backgroundColorChooseMenu.add(new BackgroundColorAction(myPanel, colorName, iconFileName, color));
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