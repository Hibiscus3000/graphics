package ru.nsu.fit.icg.lab2.filter;

public class GammaFilter implements Filter {

    @Override
    public String getName() {
        return "Гамма-коррекция";
    }

    @Override
    public String getJsonName() {
        return "gamma";
    }
}
