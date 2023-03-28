package ru.nsu.fit.icg.lab2.dialog.borders;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ThreeColorFilterDialog;
import ru.nsu.fit.icg.lab2.filter.borders.BordersFilter;

public abstract class BordersDialog extends ThreeColorFilterDialog {

    protected BordersDialog(BordersFilter bordersFilter, int min, int max, int amountToStepBy) {
        super(bordersFilter, min, max, amountToStepBy);

        VBox colorBinarizationEditBox = getColorQuantizationBox("Выберите параметр " +
                "бинаризации для каждого цвета");
        colorBinarizationEditBox.getChildren().add(getButtonBox());
        getDialogPane().setContent(colorBinarizationEditBox);
    }
}
