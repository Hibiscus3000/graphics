package ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler;

import javafx.event.Event;
import javafx.scene.control.ButtonType;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterParametersChanger;

public class FilterDialogShower extends FilterChangeHandler {

    public FilterDialogShower(ImageBox imageBox) {
        super(imageBox);
    }

    @Override
    public void handle(Event event) {
        FilterDialog filterDialog = ((FilterParametersChanger) (event.getSource())).getDialog();
        if (null != filterDialog) {
            filterDialog.showAndWait();
            if (ButtonType.OK == filterDialog.getResult()) {
                imageBox.setFilterChanged(true);
            }
        }
        super.handle(event);
    }
}
