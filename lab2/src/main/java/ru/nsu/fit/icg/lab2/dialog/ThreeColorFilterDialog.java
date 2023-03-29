package ru.nsu.fit.icg.lab2.dialog;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;

public abstract class ThreeColorFilterDialog extends FilterDialog {

    protected ThreeColorFilter threeColorFilter;
    private final IntegerValueEditBox colorPropertyEditBoxes[] = new IntegerValueEditBox[ThreeColorFilter.Color.values().length];

    protected ThreeColorFilterDialog(ThreeColorFilter threeColorFilter,
                                     int min, int max, int amountToStepBy) {
        super(threeColorFilter);
        this.threeColorFilter = threeColorFilter;
        ThreeColorFilter.Color[] colors = ThreeColorFilter.Color.values();
        prevColorValues = new int[colors.length];
        for (ThreeColorFilter.Color color : colors) {
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
        ThreeColorFilter.Color[] colors = ThreeColorFilter.Color.values();
        for (ThreeColorFilter.Color color : colors) {
            prevColorValues[color.ordinal()] = threeColorFilter.getColorProperty(color).get();
        }
    }

    @Override
    protected void cancel() {
        ThreeColorFilter.Color[] colors = ThreeColorFilter.Color.values();
        for (ThreeColorFilter.Color color : colors) {
            threeColorFilter.getColorProperty(color).set(prevColorValues[color.ordinal()]);
        }
    }

    protected VBox getColorQuantizationBox(String labelText) {
        return new VBox(new Label(labelText),
                colorPropertyEditBoxes[ThreeColorFilter.Color.RED.ordinal()],
                colorPropertyEditBoxes[ThreeColorFilter.Color.GREEN.ordinal()],
                colorPropertyEditBoxes[ThreeColorFilter.Color.BLUE.ordinal()]);
    }
}
