package ru.nsu.fit.icg.lab2.dialog.dithering;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;

public class FloydSteinbergDialog extends DitheringDialog {

    public FloydSteinbergDialog(ThreeColorFilter threeColorFilter) {
        super(threeColorFilter);
        VBox colorQuantizationBox = getColorQuantizationBox(labelText);
        colorQuantizationBox.getChildren().add(getButtonBox());
        getDialogPane().setContent(colorQuantizationBox);
    }
}
