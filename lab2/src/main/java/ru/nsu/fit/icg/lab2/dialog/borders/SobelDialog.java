package ru.nsu.fit.icg.lab2.dialog.borders;

import ru.nsu.fit.icg.lab2.filter.borders.SobelFilter;

public class SobelDialog extends BordersDialog {
    public SobelDialog(SobelFilter sobelFilter) {
        super(sobelFilter, 1, 1020, 20);
    }
}
