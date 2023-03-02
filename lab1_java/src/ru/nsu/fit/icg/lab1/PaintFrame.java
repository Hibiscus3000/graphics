package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.action.*;
import ru.nsu.fit.icg.lab1.instrument.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PaintFrame extends JFrame implements InstrumentListener, ColorListener {

    private static final int preferredSizeScale = 2;

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("Файл");
    private final JMenu instrumentMenu = new JMenu("Инструмент");
    private final JMenu colorMenu = new JMenu("Цвет");

    private final JToolBar toolBar = new JToolBar();

    public static void main(String[] args) {
        PaintFrame paintFrame = new PaintFrame();
        paintFrame.setVisible(true);
    }

    public PaintFrame() {
        setTitle("ICG Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setMinimumSize(new Dimension(640,480));
        setPreferredSize(new Dimension((int) (screenSize.getWidth() / preferredSizeScale),
                (int) (screenSize.getHeight() / preferredSizeScale)));

        createPropertyActions();
        addMenu();
        toolBar.setRollover(true);
        add(toolBar,BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
    }

    private final ButtonGroup instrumentMenuButtonGroup = new ButtonGroup();
    private final ButtonGroup instrumentToolbarButtonGroup = new ButtonGroup();
    private final ButtonGroup colorMenuButtonGroup = new ButtonGroup();
    private final ButtonGroup colorToolbarButtonGroup = new ButtonGroup();

    private void createPropertyActions() {
        addFileAction(new FileAction("Открыть","open.png"));
        addFileAction(new FileAction("Сохранить","save.png"));
        addFileAction(new FileAction("Сохранить как","save_as.png"));

        addInstrumentAction(new InstrumentAction("Прямая",this,
                new StraightLineInstrument(),"straight_line.png"));
        addInstrumentAction(new InstrumentAction("Кривая",this,
                new CurveLineInstrument(),"curve_line.png"));
        addInstrumentAction(new InstrumentAction("Овал",this,
                new OvalInstrument(),"oval.png"));
        addInstrumentAction(new InstrumentAction("Многоугольник",this,
                new PolygonInstrument(),"polygon.png"));
        addInstrumentAction(new InstrumentAction("Звезда",this,
                new StarInstrument(),"star.png"));
        addInstrumentAction(new InstrumentAction("Заливка",this,
                new FillInstrument(),"fill.png"));

        addColorAction(new ColorAction("Красный","красным",
                new Color(255,0,0),this,"red.png"));
        addColorAction(new ColorAction("Зеленый","зеленым",
                new Color(0,255,0),this,"green.png"));
        addColorAction(new ColorAction("Синий","синим",
                new Color(0,0,255),this,"blue.png"));
        addColorAction(new ColorAction("Желтый","желтным",
                new Color(255,255,0),this,"red_green.png"));
        addColorAction(new ColorAction("Розовый","розовым",
                new Color(255,0,255),this,"red_blue.png"));
    }

    private void addFileAction(FileAction fileAction) {
        JMenuItem menuItem = new JMenuItem(fileAction);
        fileMenu.add(menuItem);
        JButton button = new JButton(fileAction);
        button.setText("");
        toolBar.add(button);
    }

    private void addInstrumentAction(InstrumentAction instrumentAction) {
        instrumentMenu.add(new ParametrisedInstrumentMenu(instrumentAction,instrumentMenuButtonGroup));
        addPropertyActionToolbar(instrumentAction,instrumentToolbarButtonGroup);
    }

    private void addColorAction(ColorAction colorAction) {
        JRadioButtonMenuItem colorButton = new JRadioButtonMenuItem(colorAction);
        colorMenuButtonGroup.add(colorButton);
        colorMenu.add(colorButton);
        addPropertyActionToolbar(colorAction,colorToolbarButtonGroup);
    }

    private void addPropertyActionToolbar(PropertyAction propertyAction, ButtonGroup buttonGroup) {
        PropertyToggleButton propertyToggleButton = new PropertyToggleButton(propertyAction);
        propertyToggleButton.setName("");
        buttonGroup.add(propertyToggleButton);
        toolBar.add(propertyToggleButton);
    }

    private void addMenu() {
        menuBar.add(fileMenu);
        menuBar.add(instrumentMenu);
        menuBar.add(colorMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void setInstrument(Instrument instrument) {

    }

    @Override
    public void setColor(Color color) {

    }
}
