package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.SharpeningFilter;

public class SharpeningDialog extends MatrixTypedDialog {
    public SharpeningDialog(SharpeningFilter sharpeningFilter) {
        super(sharpeningFilter, "Выберите степень увеличения резкости");
    }
}
