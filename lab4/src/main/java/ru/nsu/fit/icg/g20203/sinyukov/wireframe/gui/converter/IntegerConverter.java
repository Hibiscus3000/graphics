package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.converter;

public class IntegerConverter extends Converter<Integer> {

    @Override
    public String toString(Integer integer) {
        return integer.toString();
    }

    @Override
    public Integer fromString(String s) {
        try {
            int newValue = Integer.parseInt(s);
            prevValue = newValue;
            return newValue;
        } catch (NumberFormatException ex) {
            formattingError();
            return prevValue;
        }
    }

    @Override
    protected String getNumberName() {
        return "целым числом";
    }
}
