package ru.nsu.fit.icg.lab2.dialog.dithering;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;

public abstract class DitheringDialog extends FilterDialog {

    protected final IntegerValueEditBox[] quantizationEditBoxes = new IntegerValueEditBox[3];

    private final static int min = 2;
    private final static int max = 128;
    private final static int amountToStepBy = 1;

    protected DitheringDialog(Filter filter) {
        super(filter);
        ditheringFilter = (DitheringFilter) filter;
        DitheringFilter ditheringFilter = (DitheringFilter) filter;
        quantizationEditBoxes[Filter.Color.RED.ordinal()] =
                new IntegerValueEditBox("Красный", min, max,
                        ditheringFilter.getQuantization(DitheringFilter.Color.RED), amountToStepBy);
        quantizationEditBoxes[Filter.Color.GREEN.ordinal()] =
                new IntegerValueEditBox("Зеленый", min, max,
                        ditheringFilter.getQuantization(DitheringFilter.Color.GREEN), amountToStepBy);
        quantizationEditBoxes[Filter.Color.BLUE.ordinal()] =
                new IntegerValueEditBox("Синий", min, max,
                        ditheringFilter.getQuantization(DitheringFilter.Color.BLUE), amountToStepBy);

        prevRedQuantization = ditheringFilter.getQuantization(DitheringFilter.Color.RED);
        prevGreenQuantization = ditheringFilter.getQuantization(DitheringFilter.Color.GREEN);
        prevBlueQuantization = ditheringFilter.getQuantization(DitheringFilter.Color.BLUE);
    }

    protected VBox getColorQuantizationBox() {
        return new VBox(new Label("Настройте числа квантования цветов"),
                quantizationEditBoxes[Filter.Color.RED.ordinal()],
                quantizationEditBoxes[Filter.Color.GREEN.ordinal()],
                quantizationEditBoxes[Filter.Color.BLUE.ordinal()]);
    }

    private int prevRedQuantization;
    private int prevGreenQuantization;
    private int prevBlueQuantization;

    @Override
    protected void saveParameters() {
        prevRedQuantization = quantizationEditBoxes[Filter.Color.RED.ordinal()].getValue();
        prevGreenQuantization = quantizationEditBoxes[Filter.Color.GREEN.ordinal()].getValue();
        prevBlueQuantization = quantizationEditBoxes[Filter.Color.BLUE.ordinal()].getValue();
    }

    @Override
    protected void cancel() {
        ditheringFilter.setQuantization(DitheringFilter.Color.RED, prevRedQuantization);
        ditheringFilter.setQuantization(DitheringFilter.Color.GREEN, prevGreenQuantization);
        ditheringFilter.setQuantization(DitheringFilter.Color.BLUE, prevBlueQuantization);
        quantizationEditBoxes[Filter.Color.RED.ordinal()].setValue(prevRedQuantization);
        quantizationEditBoxes[Filter.Color.GREEN.ordinal()].setValue(prevGreenQuantization);
        quantizationEditBoxes[Filter.Color.BLUE.ordinal()].setValue(prevBlueQuantization);
    }

    protected final DitheringFilter ditheringFilter;

    protected class QuantizationHandler implements ChangeListener<Integer> {

        protected final DitheringFilter.Color color;

        public QuantizationHandler(DitheringFilter.Color color) {
            this.color = color;
        }

        @Override
        public void changed(ObservableValue<? extends Integer> observableValue, Integer oldVal, Integer newVal) {
            ditheringFilter.setQuantization(color, quantizationEditBoxes[color.ordinal()].getValue());
        }
    }

}
