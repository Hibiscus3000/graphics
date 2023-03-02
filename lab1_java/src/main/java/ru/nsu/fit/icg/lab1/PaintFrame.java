package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.action.*;
import ru.nsu.fit.icg.lab1.instrument.*;
import ru.nsu.fit.icg.lab1.menu_item.ColorMenuRadioButton;
import ru.nsu.fit.icg.lab1.menu_item.InstrumentMenu;
import ru.nsu.fit.icg.lab1.toggle_button.ExclusiveToggleButton;
import ru.nsu.fit.icg.lab1.toggle_button.InstrumentToggleButton;

import javax.swing.*;
import java.awt.*;

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

        try {
            for (ColorAction colorAction : ColorParser.parseColorActionsJson(this)) {
                addColorAction(colorAction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFileAction(FileAction fileAction) {
        JMenuItem menuItem = new JMenuItem(fileAction);
        fileMenu.add(menuItem);
        JButton button = new JButton(fileAction);
        button.setText("");
        toolBar.add(button);
    }

    private void addInstrumentAction(InstrumentAction instrumentAction) {
        instrumentMenu.add(new InstrumentMenu(instrumentAction,instrumentMenuButtonGroup));
        addExclusiveActionToolbar(new InstrumentToggleButton(instrumentAction),instrumentToolbarButtonGroup);
    }

    private void addColorAction(ColorAction colorAction) {
        ColorMenuRadioButton colorMenuRadioButton = new ColorMenuRadioButton(colorAction);
        colorMenuButtonGroup.add(colorMenuRadioButton);
        colorMenu.add(colorMenuRadioButton);
        addExclusiveActionToolbar(new ExclusiveToggleButton(colorAction),colorToolbarButtonGroup);
    }

    private void addExclusiveActionToolbar(ExclusiveToggleButton exclusiveToggleButton, ButtonGroup buttonGroup) {
        buttonGroup.add(exclusiveToggleButton);
        toolBar.add(exclusiveToggleButton);
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
