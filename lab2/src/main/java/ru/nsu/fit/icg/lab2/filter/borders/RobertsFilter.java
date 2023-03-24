package ru.nsu.fit.icg.lab2.filter.borders;

public class RobertsFilter extends BordersFilter {

    public RobertsFilter() {
        super(new int[][]{{1, 0}, {0, -1}}, new int[][]{{0, 1}, {-1, 0}},
                2, new int[]{50, 50, 50});
    }

    @Override
    public String getName() {
        return "Фильтр Робертса";
    }

    @Override
    public String getJsonName() {
        return "roberts";
    }
}
