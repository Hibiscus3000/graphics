package ru.nsu.fit.icg.lab1;

import org.json.simple.parser.ParseException;
import ru.nsu.fit.icg.lab1.action.CommandAction;
import ru.nsu.fit.icg.lab1.action.ExclusiveAction;
import ru.nsu.fit.icg.lab1.action.InstrumentAction;
import ru.nsu.fit.icg.lab1.action.color.ArbitraryColorAction;
import ru.nsu.fit.icg.lab1.action.color.ColorAction;
import ru.nsu.fit.icg.lab1.action.file.OpenFileAction;
import ru.nsu.fit.icg.lab1.action.file.SaveFileAction;
import ru.nsu.fit.icg.lab1.action.info.InfoAction;
import ru.nsu.fit.icg.lab1.action.work_area.ClearAction;
import ru.nsu.fit.icg.lab1.action.work_area.RedoAction;
import ru.nsu.fit.icg.lab1.action.work_area.UndoAction;
import ru.nsu.fit.icg.lab1.instrument.ColoredInstrument;
import ru.nsu.fit.icg.lab1.instrument.FillInstrument;
import ru.nsu.fit.icg.lab1.instrument.Instrument;
import ru.nsu.fit.icg.lab1.instrument.ParameterizableInstrument;
import ru.nsu.fit.icg.lab1.instrument.line.CurveLineInstrument;
import ru.nsu.fit.icg.lab1.instrument.line.StraightLineInstrument;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersDialog;
import ru.nsu.fit.icg.lab1.instrument.parameter.ParametersParser;
import ru.nsu.fit.icg.lab1.instrument.resizable.EllipseInstrument;
import ru.nsu.fit.icg.lab1.instrument.resizable.PolygonInstrument;
import ru.nsu.fit.icg.lab1.instrument.resizable.StarInstrument;
import ru.nsu.fit.icg.lab1.menu_item.ColorMenuRadioButton;
import ru.nsu.fit.icg.lab1.menu_item.InstrumentMenu;
import ru.nsu.fit.icg.lab1.panel.ColorListener;
import ru.nsu.fit.icg.lab1.panel.PaintPanel;
import ru.nsu.fit.icg.lab1.toggle_button.ExclusiveToggleButton;
import ru.nsu.fit.icg.lab1.toggle_button.InstrumentToggleButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.YES_OPTION;

public class PaintFrame extends JFrame implements InstrumentParametersListener, ColorListener {

    private static final double preferredSizeScale = 0.66;

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("Файл");
    private final JMenu workAreaMenu = new JMenu("Рабочая область");
    private final JMenu instrumentMenu = new JMenu("Инструмент");
    private final JMenu colorMenu = new JMenu("Цвет");
    private final JMenu infoMenu = new JMenu("Информация");

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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (YES_OPTION == JOptionPane.showConfirmDialog(PaintFrame.this,
                        "Вы уверены, что хотите выйти?", "Выход",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
                    dispose();
                }
            }
        });

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setMinimumSize(new Dimension(640, 480));
        setPreferredSize(new Dimension((int) (preferredSizeScale * screenSize.getWidth()),
                (int) (preferredSizeScale * screenSize.getHeight())));

        createPropertyActions();
        addMenu();
        toolBar.setRollover(true);
        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(paintPanel), BorderLayout.CENTER);
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
        addAction(new OpenFileAction("Открыть", this), fileMenu);
        addAction(new SaveFileAction("Сохранить", this), fileMenu);
        toolBar.addSeparator();

        addAction(new UndoAction(paintPanel), workAreaMenu);
        addAction(new RedoAction(paintPanel), workAreaMenu);
        workAreaMenu.addSeparator();
        addAction(new ClearAction(paintPanel), workAreaMenu);
        toolBar.addSeparator();

        addInstrumentAction(new InstrumentAction("Мышь", this,
                null, "mouse.png"));
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
        toolBar.addSeparator();

        for (ColorAction colorAction : ColorParser.parseColorActionsJson(this)) {
            addColorAction(colorAction);
        }
        toolBar.addSeparator();
        colorMenu.addSeparator();
        addColorAction(new ArbitraryColorAction(this));
        toolBar.addSeparator();

        addAction(new InfoAction("Руководство пользователя",
                "guide.png",
                "Открыть руководство пользователя",
                "guide.txt",
                this), infoMenu);
        addAction(new InfoAction("О программе",
                "about.png",
                "Открыть описание программы",
                "about.txt",
                this), infoMenu);
    }

    private void addAction(CommandAction commandAction, JMenu menu) {
        JMenuItem menuItem = new JMenuItem(commandAction);
        menu.add(menuItem);
        JButton button = new JButton(commandAction);
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
        menuBar.add(workAreaMenu);
        menuBar.add(instrumentMenu);
        menuBar.add(colorMenu);
        menuBar.add(infoMenu);
        setJMenuBar(menuBar);
    }

    private final ParametersParser parametersParser = new ParametersParser();

    @Override
    public void changeInstrumentParameters(Instrument instrument) {
        if (instrument instanceof ParameterizableInstrument) {
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

    public void openImage(File file) {
        try {
            paintPanel.openImage(ImageIO.read(file));
            paintPanel.revalidate();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не получилось открыть файл"
                            + file.getName() + "\n" + e.getMessage(),
                    "Ошибка: открытие файла", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveImage(File file) {
        try {
            ImageIO.write(paintPanel.getCurrentImage(), "png", file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не получилось сохранить файл"
                            + file.getName() + "\n" + e.getMessage(),
                    "Ошибка: сохранение файла", JOptionPane.ERROR_MESSAGE);
        }
    }
}