package ru.nsu.fit.icg.lab1;

import org.json.simple.parser.ParseException;
import ru.nsu.fit.icg.lab1.action.ColorAction;
import ru.nsu.fit.icg.lab1.action.FileAction;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.instrument.*;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersDialog;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.menu_item.ColorMenuRadioButton;
import ru.nsu.fit.icg.lab1.menu_item.InstrumentMenu;
import ru.nsu.fit.icg.lab1.toggle_button.ExclusiveToggleButton;
import ru.nsu.fit.icg.lab1.toggle_button.InstrumentToggleButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class PaintFrame extends JFrame implements InstrumentParametersListener {

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
        pack();
        setLocationRelativeTo(null);
    }

    private final ButtonGroup instrumentMenuButtonGroup = new ButtonGroup();
    private final ButtonGroup instrumentToolbarButtonGroup = new ButtonGroup();
    private final ButtonGroup colorMenuButtonGroup = new ButtonGroup();
    private final ButtonGroup colorToolbarButtonGroup = new ButtonGroup();

    private void createPropertyActions() throws IOException, ParseException {
        addFileAction(new FileAction("Открыть", "open.png"));
        addFileAction(new FileAction("Сохранить", "save.png"));
        addFileAction(new FileAction("Сохранить как", "save_as.png"));

        addInstrumentAction(new InstrumentAction("Прямая", paintPanel, this,
                new StraightLineInstrument(parametersParser), "straight_line.png"));
        addInstrumentAction(new InstrumentAction("Кривая", paintPanel, this,
                new CurveLineInstrument(parametersParser), "curve_line.png"));
        addInstrumentAction(new InstrumentAction("Эллипс", paintPanel, this,
                new EllipseInstrument(parametersParser), "ellipse.png"));
        addInstrumentAction(new InstrumentAction("Многоугольник", paintPanel, this,
                new PolygonInstrument(parametersParser), "polygon.png"));
        addInstrumentAction(new InstrumentAction("Звезда", paintPanel, this,
                new StarInstrument(parametersParser), "star.png"));
        addInstrumentAction(new InstrumentAction("Заливка", paintPanel, this,
                new FillInstrument(parametersParser), "fill.png"));

        for (ColorAction colorAction : ColorParser.parseColorActionsJson(paintPanel)) {
            addColorAction(colorAction);
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
        }
        instrumentDialog.setVisible(true);
        instrument.changeParameters(instrumentDialog);
    }
}
