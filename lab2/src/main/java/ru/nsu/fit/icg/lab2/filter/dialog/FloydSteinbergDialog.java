package ru.nsu.fit.icg.lab2.filter.dialog;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.FloydSteinbergFilter;

public class FloydSteinbergDialog extends FilterDialog {

    private final IntegerValueEditBox redQuantizationEditBox;
    private final IntegerValueEditBox greenQuantizationEditBox;
    private final IntegerValueEditBox blueQuantizationEditBox;

    private final static int min = 2;
    private final static int max = 128;
    private final static int amountToStepBy = 2;

    public FloydSteinbergDialog(Filter filter) {
        super(filter);
        FloydSteinbergFilter floydSteinbergFilter = (FloydSteinbergFilter) filter;
        redQuantizationEditBox = new IntegerValueEditBox("Красный", min, max,
                floydSteinbergFilter.getRedQuantization(), amountToStepBy);
        greenQuantizationEditBox = new IntegerValueEditBox("Зеленый", min, max,
                floydSteinbergFilter.getGreenQuantization(), amountToStepBy);
        blueQuantizationEditBox = new IntegerValueEditBox("Синий", min, max,
                floydSteinbergFilter.getBlueQuantization(), amountToStepBy);

        VBox vBox = new VBox(new Label("Выберите числа квантования"),
                redQuantizationEditBox, greenQuantizationEditBox, blueQuantizationEditBox, getButtonBox());
        getDialogPane().setContent(vBox);
    }

    @Override
    protected void changeValues() {
        ((FloydSteinbergFilter) filter).setRedQuantization(redQuantizationEditBox.getValue());
        ((FloydSteinbergFilter) filter).setGreenQuantization(greenQuantizationEditBox.getValue());
        ((FloydSteinbergFilter) filter).setBlueQuantization(blueQuantizationEditBox.getValue());
    }
}
