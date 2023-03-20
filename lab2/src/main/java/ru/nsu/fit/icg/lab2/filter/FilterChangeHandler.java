package ru.nsu.fit.icg.lab2.filter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ru.nsu.fit.icg.lab2.DrawParent;

public class FilterChangeHandler implements EventHandler<ActionEvent> {

    private final DrawParent drawParent;

    public FilterChangeHandler(DrawParent drawParent) {
        this.drawParent = drawParent;
    }

    @Override
    public void handle(ActionEvent event) {
        ((FilterChanger) (event.getSource())).getFilter();
    }
}
