package ru.nsu.fit.icg.lab2.dialog;

import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class FilterDialog extends InstrumentDialog {

    protected final Filter filter;

    public FilterDialog(Filter filter) {
        this.filter = filter;
        setTitle(filter.getName());
    }

}
