package ru.nsu.fit.icg.lab2.dialog.borders;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.borders.BordersFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;

public abstract class BordersDialog extends FilterDialog {

    private final BordersFilter bordersFilter;

    private final int min;
    private final int max;
    private final int amountToStepBy;

    private final IntegerValueEditBox[] binarizationEditBoxes = new IntegerValueEditBox[3];

    protected BordersDialog(Filter filter, int min, int max, int amountToStepBy) {
        super(filter);
        this.min = min;
        this.max = max;
        this.amountToStepBy = amountToStepBy;
        bordersFilter = (BordersFilter) filter;
        prevRedBinarization = bordersFilter.getBinarization(Filter.Color.RED);
        prevGreenBinarization = bordersFilter.getBinarization(Filter.Color.GREEN);
        prevBlueBinarization = bordersFilter.getBinarization(Filter.Color.BLUE);

        binarizationEditBoxes[Filter.Color.RED.ordinal()] =
                new IntegerValueEditBox("Красный", min, max,
                        bordersFilter.getBinarization(DitheringFilter.Color.RED), amountToStepBy);
        binarizationEditBoxes[Filter.Color.GREEN.ordinal()] =
                new IntegerValueEditBox("Зеленый", min, max,
                        bordersFilter.getBinarization(DitheringFilter.Color.GREEN), amountToStepBy);
        binarizationEditBoxes[Filter.Color.BLUE.ordinal()] =
                new IntegerValueEditBox("Синий", min, max,
                        bordersFilter.getBinarization(DitheringFilter.Color.BLUE), amountToStepBy);

        binarizationEditBoxes[Filter.Color.RED.ordinal()].setChangeHandler(new BinarizationHandler(Filter.Color.RED));
        binarizationEditBoxes[Filter.Color.GREEN.ordinal()].setChangeHandler(new BinarizationHandler(Filter.Color.GREEN));
        binarizationEditBoxes[Filter.Color.BLUE.ordinal()].setChangeHandler(new BinarizationHandler(Filter.Color.BLUE));

        VBox colorBinarizationEditBox = new VBox(new Label("Выберите параметр бинаризации для каждого цвета"),
                binarizationEditBoxes[Filter.Color.RED.ordinal()],
                binarizationEditBoxes[Filter.Color.GREEN.ordinal()],
                binarizationEditBoxes[Filter.Color.BLUE.ordinal()],
                getButtonBox());
        getDialogPane().setContent(colorBinarizationEditBox);
    }

    private int prevRedBinarization;
    private int prevGreenBinarization;
    private int prevBlueBinarization;

    @Override
    protected void saveParameters() {
        prevRedBinarization = binarizationEditBoxes[Filter.Color.RED.ordinal()].getValue();
        prevGreenBinarization = binarizationEditBoxes[Filter.Color.GREEN.ordinal()].getValue();
        prevBlueBinarization = binarizationEditBoxes[Filter.Color.BLUE.ordinal()].getValue();
    }

    @Override
    protected void cancel() {
        bordersFilter.setBinarization(DitheringFilter.Color.RED, prevRedBinarization);
        bordersFilter.setBinarization(DitheringFilter.Color.GREEN, prevGreenBinarization);
        bordersFilter.setBinarization(DitheringFilter.Color.BLUE, prevBlueBinarization);
        binarizationEditBoxes[Filter.Color.RED.ordinal()].setValue(prevRedBinarization);
        binarizationEditBoxes[Filter.Color.GREEN.ordinal()].setValue(prevGreenBinarization);
        binarizationEditBoxes[Filter.Color.BLUE.ordinal()].setValue(prevBlueBinarization);
    }

    protected class BinarizationHandler implements ChangeListener<Integer> {

        protected final DitheringFilter.Color color;

        public BinarizationHandler(DitheringFilter.Color color) {
            this.color = color;
        }

        @Override
        public void changed(ObservableValue<? extends Integer> observableValue, Integer oldVal, Integer newVal) {
            bordersFilter.setBinarization(color, binarizationEditBoxes[color.ordinal()].getValue());
        }
    }
}
