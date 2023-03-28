package ru.nsu.fit.icg.lab2.dialog.dithering;

import ru.nsu.fit.icg.lab2.dialog.ThreeColorFilterDialog;
import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;

public abstract class DitheringDialog extends ThreeColorFilterDialog {

    protected String labelText = "Выберите число квантования для каждого цвета";

    protected DitheringDialog(ThreeColorFilter threeColorFilter) {
        super(threeColorFilter, 2, 128, 1);
    }
}
