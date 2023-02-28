package ru.nsu.fit.icg.lab1;

import ru.nsu.fit.icg.lab1.action.ColorAction;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.instrument.FillInstrument;
import ru.nsu.fit.icg.lab1.instrument.Instrument;
import ru.nsu.fit.icg.lab1.instrument.LineInstrument;
import ru.nsu.fit.icg.lab1.instrument.StampInstrument;
import ru.nsu.fit.icg.lab1.menu.FillMenu;
import ru.nsu.fit.icg.lab1.menu.LineMenu;
import ru.nsu.fit.icg.lab1.menu.StampMenu;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class PaintFrame extends JFrame implements InstrumentListener, ColorListener {

    private static final int preferredSizeScale = 2;

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

        addMenu();
        addToolbar();
        pack();
        setLocationRelativeTo(null);
    }

    private final FillInstrument fillInstrument = new FillInstrument();
    private final LineInstrument lineInstrument = new LineInstrument();
    private final StampInstrument stampInstrument = new StampInstrument();

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Открыть");
        JMenuItem saveItem = new JMenuItem("Сохранить");
        JMenuItem saveAsItem = new JMenuItem("Сохранить как");
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        menuBar.add(fileMenu);

        ButtonGroup instrumentButtonGroup = new ButtonGroup();

        JMenu shapeMenu = new JMenu("Форма");
        LineMenu lineMenu = new LineMenu(lineInstrument, this,instrumentButtonGroup,true);
        StampMenu stampMenu = new StampMenu(stampInstrument, this,instrumentButtonGroup,false);
        shapeMenu.add(lineMenu);
        shapeMenu.add(stampMenu);
        menuBar.add(shapeMenu);

        JMenu selectAndFillMenu = new JMenu("Выделение и заливка");
        FillMenu fillMenu = new FillMenu(fillInstrument, this, instrumentButtonGroup, false);
        JMenuItem selectItem = new JMenuItem("Выделение");
        JMenuItem cleanItem = new JMenuItem("Очистка");
        selectAndFillMenu.add(fillMenu);
        selectAndFillMenu.add(selectItem);
        selectAndFillMenu.add(cleanItem);
        menuBar.add(selectAndFillMenu);
    }

    private void addToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setRollover(true);

        ButtonGroup instrumentButtonGroup = new ButtonGroup();


        ButtonGroup colorButtonGroup1 = new ButtonGroup();
        ColorAction redAction = new ColorAction("красным", this, "red.png");
        addToggleButtonToToolbar(toolBar, colorButtonGroup1, redAction);
        ColorAction greenAction = new ColorAction("зеленым", this, "green.png");
        addToggleButtonToToolbar(toolBar, colorButtonGroup1, greenAction);
        ColorAction blueAction = new ColorAction("синим", this, "blue.png");
        addToggleButtonToToolbar(toolBar, colorButtonGroup1, blueAction);

        add(toolBar, BorderLayout.NORTH);
    }

    private void addToggleButtonToToolbar(JToolBar toolBar, ButtonGroup buttonGroup, Action action) {
        JToggleButton toggleButton = new JToggleButton(action);
        buttonGroup.add(toggleButton);
        toolBar.add(toggleButton);
    }

    @Override
    public void setInstrument(Instrument instrument) {

    }

    @Override
    public void setColor() {

    }
}
