package ru.nsu.fit.icg.lab1;

import org.json.simple.parser.ParseException;
import ru.nsu.fit.icg.lab1.action.ExclusiveAction;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.action.color.ArbitraryColorAction;
import ru.nsu.fit.icg.lab1.action.color.ColorAction;
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
import ru.nsu.fit.icg.lab1.panel.ColorListener;
import ru.nsu.fit.icg.lab1.panel.PaintPanel;
import ru.nsu.fit.icg.lab1.toggle_button.ExclusiveToggleButton;
import ru.nsu.fit.icg.lab1.toggle_button.InstrumentToggleButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class PaintFrame extends JFrame implements InstrumentParametersListener, ColorListener {

    private static final int preferredSizeScale = 2;

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("Файл");
    private final JMenu instrumentMenu = new JMenu("Инструмент");
    private final JMenu colorMenu = new JMenu("Цвет");

    private final JToolBar toolBar = new JToolBar();

    private final PaintPanel paintPanel = new PaintPanel();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PaintFrame paintFrame = new PaintFrame();
                paintFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
        colorToolbarButtonGroup.getElements().nextElement().doClick();
        instrumentMenuButtonGroup.getElements().nextElement().doClick();
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

        addInstrumentAction(new InstrumentAction("Ничего", this, null,
                "fill.png"));
        addInstrumentAction(new InstrumentAction("Прямая", this,
                new StraightLineInstrument(parametersParser, paintPanel), "straight_line.png"));
        addInstrumentAction(new InstrumentAction("Кривая", this,
                new CurveLineInstrument(parametersParser, paintPanel), "curve_line.png"));
        addInstrumentAction(new InstrumentAction("Эллипс", this,
                new EllipseInstrument(parametersParser, paintPanel), "ellipse.png"));
        addInstrumentAction(new InstrumentAction("Многоугольник", this,
                new PolygonInstrument(parametersParser, paintPanel), "polygon.png"));
        addInstrumentAction(new InstrumentAction("Звезда", this,
                new StarInstrument(parametersParser, paintPanel), "star.png"));
        addInstrumentAction(new InstrumentAction("Заливка", this,
                new FillInstrument(paintPanel), "fill.png"));

        for (ColorAction colorAction : ColorParser.parseColorActionsJson(this)) {
            addColorAction(colorAction);
        }
        addColorAction(new ArbitraryColorAction(this));
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

    private void addColorAction(ExclusiveAction colorAction) {
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

    @Override
    public void changeInstrumentParameters(Instrument instrument) {
        if (null != instrument && instrument instanceof ParameterizableInstrument) {
            new ParametersDialog(this, instrument.getName(),
                    parametersParser.getParametersMap(instrument.getClass().getName()))
                    .setVisible(true);
        }
    }

    private Color color;
    private Instrument instrument;

    @Override
    public void setInstrument(Instrument instrument) {
        if (instrument instanceof ColoredInstrument) {
            ((ColoredInstrument) instrument).setColor(color);
        }
        paintPanel.setInstrument(instrument);
        this.instrument = instrument;
    }

    private void clearColorSelection() {
        colorMenuButtonGroup.clearSelection();
        colorToolbarButtonGroup.clearSelection();
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        if (null != instrument && instrument instanceof ColoredInstrument) {
            ((ColoredInstrument) instrument).setColor(color);
        }
    }

    @Override
    public void showColorDialog(JColorChooser colorChooser) {
        JColorChooser.createDialog(
                PaintFrame.this,
                "Выбор произвольного цвета",
                true,
                colorChooser,
                event -> setColor(colorChooser.getColor()),
                null).setVisible(true);
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