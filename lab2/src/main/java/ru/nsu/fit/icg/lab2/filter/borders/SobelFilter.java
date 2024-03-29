package ru.nsu.fit.icg.lab2.filter.borders;

public class SobelFilter extends BordersFilter {

    public SobelFilter() {
        super(new int[][]{{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}},
                new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}},
                4, 300);
    }

    @Override
    public String getName() {
        return "Фильтр Собеля";
    }

    @Override
    public String getJsonName() {
        return "sobel";
    }
    //<a target="_blank" href="https://icons8.com/icon/OJMsgQOCrlH7/circled-s">Circled S</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
