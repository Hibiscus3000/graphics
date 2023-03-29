package ru.nsu.fit.icg.lab2.dialog;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;

public abstract class ThreeColorFilterDialog extends FilterDialog {

    protected ThreeColorFilter threeColorFilter;
    private final IntegerValueEditBox colorPropertyEditBoxes[] = new IntegerValueEditBox[Filter.Color.values().length];

    protected ThreeColorFilterDialog(ThreeColorFilter threeColorFilter,
                                     int min, int max, int amountToStepBy) {
        super(threeColorFilter);
        this.threeColorFilter = threeColorFilter;
        Filter.Color[] colors = Filter.Color.values();
        prevColorValues = new int[colors.length];
        for (Filter.Color color : colors) {
            IntegerProperty colorProperty = threeColorFilter.getColorProperty(color);
            int colorOrdinal = color.ordinal();
            colorPropertyEditBoxes[colorOrdinal] =
                    new IntegerValueEditBox(color.getName(), colorProperty, min, max, amountToStepBy);
            prevColorValues[colorOrdinal] = colorProperty.get();
        }
    }

    protected int[] prevColorValues;

    @Override
    protected void saveParameters() {
        Filter.Color[] colors = Filter.Color.values();
        for (Filter.Color color : colors) {
            prevColorValues[color.ordinal()] = threeColorFilter.getColorProperty(color).get();
        }
    }

    @Override
    protected void cancel() {
        Filter.Color[] colors = Filter.Color.values();
        for (Filter.Color color : colors) {
            threeColorFilter.getColorProperty(color).set(prevColorValues[color.ordinal()]);
        }
    }

    protected VBox getColorQuantizationBox(String labelText) {
        return new VBox(new Label(labelText),
                colorPropertyEditBoxes[Filter.Color.RED.ordinal()],
                colorPropertyEditBoxes[Filter.Color.GREEN.ordinal()],
                colorPropertyEditBoxes[Filter.Color.BLUE.ordinal()]);
    }
}
