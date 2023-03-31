package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.EmbossingFilter;

public class EmbossingDialog extends MatrixTypedDialog {

    public EmbossingDialog(EmbossingFilter embossingFilter) {
        super(embossingFilter, "Выберите направление тиснения");
    }
}
