package ru.nsu.fit.icg.lab2.dialog.borders;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.editBox.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.filter.borders.BordersFilter;

public abstract class BordersDialog extends FilterDialog {

    private final IntegerValueEditBox binarizationEditBox;
    private final CheckBox blackWhiteCheckBox;
    private final CheckBox sharpBordersCheckBox;

    protected BordersDialog(BordersFilter bordersFilter,
                            int min, int max, int amountToStepBy) {
        super(bordersFilter);

        binarizationEditBox = new IntegerValueEditBox(
                "Выберите параметр бинаризации",
                bordersFilter.binarizationProperty(),
                min, max, amountToStepBy);
        blackWhiteCheckBox = new CheckBox("Сделать выделение черно-белым");
        BooleanProperty blackWhiteBorders = bordersFilter.blackWhiteBordersProperty();
        blackWhiteCheckBox.selectedProperty().bindBidirectional(blackWhiteBorders);
        prevBlackWhite = blackWhiteBorders.get();
        sharpBordersCheckBox = new CheckBox("Резкое выделение границ");
        BooleanProperty sharpBorders = bordersFilter.sharpBordersProperty();
        sharpBordersCheckBox.selectedProperty().bindBidirectional(sharpBorders);
        prevSharpBorders = blackWhiteBorders.get();
        VBox colorBinarizationEditBox = new VBox(binarizationEditBox, blackWhiteCheckBox, sharpBordersCheckBox);
        colorBinarizationEditBox.getStyleClass().add("color-binarization-edit-box");
        colorBinarizationEditBox.getChildren().add(getButtonBox());
        getDialogPane().setContent(colorBinarizationEditBox);
    }

    private boolean prevBlackWhite;
    private boolean prevSharpBorders;

    @Override
    protected void ok() {
        binarizationEditBox.ok();
        prevBlackWhite = blackWhiteCheckBox.isSelected();
        prevSharpBorders = sharpBordersCheckBox.isSelected();
    }

    @Override
    protected void cancel() {
        binarizationEditBox.cancel();
        blackWhiteCheckBox.setSelected(prevBlackWhite);
        sharpBordersCheckBox.setSelected(prevSharpBorders);
    }
}
