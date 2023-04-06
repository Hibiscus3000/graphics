package ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler;

import javafx.event.Event;
import javafx.event.EventHandler;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterChanger;

public class FilterChangeHandler implements EventHandler<Event> {

    protected final ImageBox imageBox;

    public FilterChangeHandler(ImageBox imageBox) {
        this.imageBox = imageBox;
    }

    @Override
    public void handle(Event event) {
        Filter filter = ((FilterChanger) (event.getSource())).getFilter();
        imageBox.setFilter(filter);
        filter.isSelectedProperty().set(true);
        event.consume();
    }
}
