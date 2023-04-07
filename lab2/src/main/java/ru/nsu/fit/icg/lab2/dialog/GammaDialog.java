package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.DoubleValueEditBox;
import ru.nsu.fit.icg.lab2.filter.GammaFilter;

public class GammaDialog extends FilterDialog {

    private final DoubleValueEditBox gammaEditBox;

    public GammaDialog(GammaFilter filter) {
        super(filter);
        GammaFilter gammaFilter = (GammaFilter) filter;

        VBox vBox = new VBox();

        gammaEditBox = new DoubleValueEditBox("Гамма", gammaFilter.gammaProperty(),
                0.1, 10.0, 0.1);
        vBox.getChildren().addAll(gammaEditBox, getButtonBox());
        getDialogPane().setContent(vBox);
    }

    @Override
    protected void ok() {
        gammaEditBox.ok();
    }

    @Override
    protected void cancel() {
        gammaEditBox.cancel();
    }
}
