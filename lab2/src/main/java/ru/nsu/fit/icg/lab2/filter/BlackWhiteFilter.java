package ru.nsu.fit.icg.lab2.filter;

public class BlackWhiteFilter implements Filter {

    @Override
    public String getName() {
        return "Черно-белый фильтр";
    }

    @Override
    public String getJsonName() {
        return "blackWhite";
    }
}
