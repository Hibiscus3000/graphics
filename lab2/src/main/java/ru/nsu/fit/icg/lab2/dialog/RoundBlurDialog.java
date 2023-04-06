package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.DoubleValueEditBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.filter.RoundBlurFilter;

public class RoundBlurDialog extends FilterDialog {

    private final IntegerValueEditBox windowSizeEditBox;
    private final DoubleValueEditBox startRelativeRadiusEditBox;
    private final DoubleValueEditBox endRelativeRadiusEditBox;

    public RoundBlurDialog(RoundBlurFilter roundBlurFilter) {
        super(roundBlurFilter);
        windowSizeEditBox = new IntegerValueEditBox("Размер окна",
                roundBlurFilter.windowSizeProperty(),
                3, 30, 1);
        startRelativeRadiusEditBox = new DoubleValueEditBox("Начало",
                roundBlurFilter.startRelativeRadiusProperty(),
                0.0, 1.0, 0.01);
        endRelativeRadiusEditBox = new DoubleValueEditBox("Конец",
                roundBlurFilter.endRelativeRadiusProperty(),
                0.0, 1.0, 0.01);
        VBox roundBlurBox = new VBox(windowSizeEditBox, startRelativeRadiusEditBox,
                endRelativeRadiusEditBox, getButtonBox());
        getDialogPane().setContent(roundBlurBox);
    }

    @Override
    protected void ok() {
        windowSizeEditBox.ok();
        startRelativeRadiusEditBox.ok();
        endRelativeRadiusEditBox.ok();
    }

    @Override
    protected void cancel() {
        windowSizeEditBox.cancel();
        startRelativeRadiusEditBox.cancel();
        endRelativeRadiusEditBox.cancel();
    }
}
