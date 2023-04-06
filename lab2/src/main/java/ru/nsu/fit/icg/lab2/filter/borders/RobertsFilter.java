package ru.nsu.fit.icg.lab2.filter.borders;

public class RobertsFilter extends BordersFilter {

    public RobertsFilter() {
        super(new int[][]{{1, 0}, {0, -1}}, new int[][]{{0, 1}, {-1, 0}}, 1, 80);
    }

    @Override
    public String getName() {
        return "Фильтр Робертса";
    }

    @Override
    public String getJsonName() {
        return "roberts";
    }
    //<a target="_blank" href="https://icons8.com/icon/u4f3nCAjFwyp/circled-r">Circled R</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
