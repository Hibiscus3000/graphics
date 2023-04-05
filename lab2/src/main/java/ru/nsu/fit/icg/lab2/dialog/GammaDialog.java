package ru.nsu.fit.icg.lab2.dialog;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.DoubleEditBox;
import ru.nsu.fit.icg.lab2.filter.GammaFilter;

public class GammaDialog extends FilterDialog {

    private final DoubleEditBox gammaEditBox;

    public GammaDialog(GammaFilter filter) {
        super(filter);
        GammaFilter gammaFilter = (GammaFilter) filter;

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);

        gammaEditBox = new DoubleEditBox("Гамма", gammaFilter.gammaProperty(),
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
