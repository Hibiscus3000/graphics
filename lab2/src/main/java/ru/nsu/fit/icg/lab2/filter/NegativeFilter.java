package ru.nsu.fit.icg.lab2.filter;

public class NegativeFilter implements Filter {

    @Override
    public String getName() {
        return "Негатив";
    }

    @Override
    public String getJsonName() {
        return "negative";
    }
}
