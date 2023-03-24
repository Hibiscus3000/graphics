package ru.nsu.fit.icg.lab2.filter.dialog.dithering;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.filter.dialog.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;

public abstract class DitheringDialog extends FilterDialog {

    protected final IntegerValueEditBox redQuantizationEditBox;
    protected final IntegerValueEditBox greenQuantizationEditBox;
    protected final IntegerValueEditBox blueQuantizationEditBox;

    private final static int min = 2;
    private final static int max = 128;
    private final static int amountToStepBy = 2;

    protected DitheringDialog(Filter filter) {
        super(filter);
        ditheringFilter = (DitheringFilter) filter;
        DitheringFilter ditheringFilter = (DitheringFilter) filter;
        redQuantizationEditBox =
                new IntegerValueEditBox("Красный", min, max,
                        ditheringFilter.getQuantization(DitheringFilter.Color.RED), amountToStepBy);
        greenQuantizationEditBox =
                new IntegerValueEditBox("Зеленый", min, max,
                        ditheringFilter.getQuantization(DitheringFilter.Color.GREEN), amountToStepBy);
        blueQuantizationEditBox =
                new IntegerValueEditBox("Синий", min, max,
                        ditheringFilter.getQuantization(DitheringFilter.Color.BLUE), amountToStepBy);

        prevRedQuantization = ditheringFilter.getQuantization(DitheringFilter.Color.RED);
        prevGreenQuantization = ditheringFilter.getQuantization(DitheringFilter.Color.GREEN);
        prevBlueQuantization = ditheringFilter.getQuantization(DitheringFilter.Color.BLUE);
    }

    protected VBox getColorQuantizationBox() {
        return new VBox(new Label("Настройте числа квантования цветов"),
                redQuantizationEditBox, greenQuantizationEditBox, blueQuantizationEditBox);
    }

    private int prevRedQuantization;
    private int prevGreenQuantization;
    private int prevBlueQuantization;

    @Override
    protected void cancel() {
        ditheringFilter.setQuantization(DitheringFilter.Color.RED, prevRedQuantization);
        ditheringFilter.setQuantization(DitheringFilter.Color.GREEN, prevGreenQuantization);
        ditheringFilter.setQuantization(DitheringFilter.Color.BLUE, prevBlueQuantization);
    }

    protected final DitheringFilter ditheringFilter;

    protected class QuantizationHandler implements InvalidationListener {

        protected final DitheringFilter.Color color;

        public QuantizationHandler(DitheringFilter.Color color) {
            this.color = color;
        }

        @Override
        public void invalidated(Observable observable) {
            ditheringFilter.setQuantization(color, redQuantizationEditBox.getValue());
        }
    }

}
