package ru.nsu.fit.icg.lab2.dialog;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.ValueEditBox;
import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;

public abstract class ThreeColorFilterDialog extends FilterDialog {

    private final ValueEditBox colorPropertyEditBoxes[] = new ValueEditBox[ThreeColorFilter.Color.values().length];

    protected ThreeColorFilterDialog(ThreeColorFilter threeColorFilter,
                                     int min, int max, int amountToStepBy) {
        super(threeColorFilter);
        for (ThreeColorFilter.Color color : ThreeColorFilter.Color.values()) {
            IntegerProperty colorProperty = threeColorFilter.getColorProperty(color);
            int colorOrdinal = color.ordinal();
            colorPropertyEditBoxes[colorOrdinal] =
                    new IntegerValueEditBox(color.getName(), colorProperty, min, max, amountToStepBy);
        }
    }


    @Override
    protected void ok() {
        for (ThreeColorFilter.Color color : ThreeColorFilter.Color.values()) {
            colorPropertyEditBoxes[color.ordinal()].ok();
        }
    }

    @Override
    protected void cancel() {
        for (ThreeColorFilter.Color color : ThreeColorFilter.Color.values()) {
            colorPropertyEditBoxes[color.ordinal()].cancel();
        }
    }

    protected VBox getColorQuantizationBox(String labelText) {
        return new VBox(new Label(labelText),
                colorPropertyEditBoxes[ThreeColorFilter.Color.RED.ordinal()],
                colorPropertyEditBoxes[ThreeColorFilter.Color.GREEN.ordinal()],
                colorPropertyEditBoxes[ThreeColorFilter.Color.BLUE.ordinal()]);
    }
}
