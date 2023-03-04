package ru.nsu.fit.icg.lab1;

import org.json.simple.parser.ParseException;
import ru.nsu.fit.icg.lab1.action.ColorAction;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.action.file.FileAction;
import ru.nsu.fit.icg.lab1.action.file.OpenFileAction;
import ru.nsu.fit.icg.lab1.action.file.SaveFileAction;
import ru.nsu.fit.icg.lab1.instrument.*;
import ru.nsu.fit.icg.lab1.instrument.line.CurveLineInstrument;
import ru.nsu.fit.icg.lab1.instrument.line.StraightLineInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersDialog;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.menu_item.ColorMenuRadioButton;
import ru.nsu.fit.icg.lab1.menu_item.InstrumentMenu;
import ru.nsu.fit.icg.lab1.panel.ColorSelectionListener;
import ru.nsu.fit.icg.lab1.panel.PaintPanel;
import ru.nsu.fit.icg.lab1.toggle_button.ExclusiveToggleButton;
import ru.nsu.fit.icg.lab1.toggle_button.InstrumentToggleButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PaintFrame extends JFrame implements InstrumentParametersListener, ColorSelectionListener {

    private static final int preferredSizeScale = 2;

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("Файл");
    private final JMenu instrumentMenu = new JMenu("Инструмент");
    private final JMenu colorMenu = new JMenu("Цвет");

    private final JToolBar toolBar = new JToolBar();

    private final PaintPanel paintPanel = new PaintPanel();

    public static void main(String[] args) {
        PaintFrame paintFrame = null;
        try {
            paintFrame = new PaintFrame();
            paintFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaintFrame() throws IOException, ParseException {
        super();
        setTitle("ICG Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setMinimumSize(new Dimension(640, 480));
        setPreferredSize(new Dimension((int) (screenSize.getWidth() / preferredSizeScale),
                (int) (screenSize.getHeight() / preferredSizeScale)));

        createPropertyActions();
        addMenu();
        toolBar.setRollover(true);
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(paintPanel), BorderLayout.CENTER);
        menuBar.add(new JMenuItem(new ClearAction()));
        pack();
        setLocationRelativeTo(null);
    }

    private final ButtonGroup instrumentMenuButtonGroup = new ButtonGroup();
    private final ButtonGroup instrumentToolbarButtonGroup = new ButtonGroup();
    private final ButtonGroup colorMenuButtonGroup = new ButtonGroup();
    private final ButtonGroup colorToolbarButtonGroup = new ButtonGroup();

    private void createPropertyActions() throws IOException, ParseException {
        addFileAction(new OpenFileAction("Открыть", this));
        addFileAction(new SaveFileAction("Сохранить", this));

        addInstrumentAction(new InstrumentAction("Прямая", paintPanel, this, this,
                new StraightLineInstrument(parametersParser, paintPanel), "straight_line.png"));
        addInstrumentAction(new InstrumentAction("Кривая", paintPanel, this, this,
                new CurveLineInstrument(parametersParser, paintPanel), "curve_line.png"));
        addInstrumentAction(new InstrumentAction("Эллипс", paintPanel, this, this,
                new EllipseInstrument(parametersParser, paintPanel), "ellipse.png"));
        addInstrumentAction(new InstrumentAction("Многоугольник", paintPanel, this, this,
                new PolygonInstrument(parametersParser, paintPanel), "polygon.png"));
        addInstrumentAction(new InstrumentAction("Звезда", paintPanel, this, this,
                new StarInstrument(parametersParser, paintPanel), "star.png"));
        addInstrumentAction(new InstrumentAction("Заливка", paintPanel, this, this,
                new FillInstrument(parametersParser, paintPanel), "fill.png"));

        for (ColorAction colorAction : ColorParser.parseColorActionsJson(paintPanel)) {
            addColorAction(colorAction);
        }
        colorMenu.add(new ArbitraryColorAction());
    }

    private void addFileAction(FileAction fileAction) {
        JMenuItem menuItem = new JMenuItem(fileAction);
        fileMenu.add(menuItem);
        JButton button = new JButton(fileAction);
        button.setText("");
        toolBar.add(button);
    }

    private void addInstrumentAction(InstrumentAction instrumentAction) {
        instrumentMenu.add(new InstrumentMenu(instrumentAction, instrumentMenuButtonGroup));
        addExclusiveActionToolbar(new InstrumentToggleButton(instrumentAction), instrumentToolbarButtonGroup);
    }

    private void addColorAction(ColorAction colorAction) {
        ColorMenuRadioButton colorMenuRadioButton = new ColorMenuRadioButton(colorAction);
        colorMenuButtonGroup.add(colorMenuRadioButton);
        colorMenu.add(colorMenuRadioButton);
        addExclusiveActionToolbar(new ExclusiveToggleButton(colorAction), colorToolbarButtonGroup);
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

    private final ParametersParser parametersParser = new ParametersParser();
    private final HashMap<String, ParametersDialog> instrumentDialogMap = new HashMap<>();

    @Override
    public void changeInstrumentParameters(Instrument instrument) {
        ParametersDialog instrumentDialog = instrumentDialogMap
                .get(instrument.getClass().getSimpleName());
        if (null == instrumentDialog) {
            instrumentDialog = new ParametersDialog(this, instrument.getName(),
                    parametersParser.getParametersMap(instrument.getParameterGroupNames()));
            instrumentDialogMap.put(instrument.getClass().getSimpleName(), instrumentDialog);
        }
        instrumentDialog.setVisible(true);
        instrument.changeParameters(instrumentDialog);
    }

    @Override
    public void clearColorSelection() {
        colorMenuButtonGroup.clearSelection();
        colorToolbarButtonGroup.clearSelection();
    }

    private class ClearAction extends AbstractAction {

        public ClearAction() {
            putValue(NAME, "Очистка");
            putValue(SHORT_DESCRIPTION, "Очистка рабочей области");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    private class ArbitraryColorAction extends AbstractAction {

        private final JColorChooser colorChooser = new JColorChooser();

        public ArbitraryColorAction() {
            putValue(NAME, "Произвольный цвет");
            putValue(SHORT_DESCRIPTION, "Выбор произвольного цвета");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JColorChooser.createDialog(
                    PaintFrame.this,
                    "Выбор произвольного цвета",
                    true,
                    colorChooser,
                    event -> paintPanel.setColor(colorChooser.getColor()),
                    null).setVisible(true);
            clearColorSelection();
        }
    }

    public void openImage(File file) {
        try {
            paintPanel.openImage(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не получилось загрузить файл"
                    + file.getName());
        }
    }

    public File saveImage(File file) {
        try {
            ImageIO.write(paintPanel.getCurrentImage(), "png", file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не получилось сохранить файл"
                    + file.getName());
        }
        return file;
    }
}