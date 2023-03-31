package ru.nsu.fit.icg.lab2.menuToolbar.toolbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.image_box.ImageBox;

public class FilterChangeHandler implements EventHandler<ActionEvent> {

    private final ImageBox imageBox;

    public FilterChangeHandler(ImageBox imageBox) {
        this.imageBox = imageBox;
    }

    @Override
    public void handle(ActionEvent event) {
        Filter filter = ((FilterChanger) (event.getSource())).getFilter();
        imageBox.setFilter(filter);
        event.consume();
    }
}
