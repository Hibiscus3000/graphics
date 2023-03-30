package ru.nsu.fit.icg.lab2.filter.window.convolution.typed;

public class SharpeningFilter extends MatrixTypedFilter {

    public enum SharpeningType implements MatrixType {
        WEAK("Слабое", new int[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}}),
        STRONG("Сильное", new int[][]{
                {-1, -1, -1},
                {-1, 9, -1},
                {-1, -1, -1}});

        private final String name;
        private final int[][] matrix;

        SharpeningType(String name, int[][] matrix) {
            this.name = name;
            this.matrix = matrix;
        }


        @Override
        public String getName() {
            return name;
        }

        @Override
        public int[][] getMatrix() {
            return matrix;
        }
    }

    public SharpeningFilter() {
        super(SharpeningType.WEAK);
    }

    @Override
    public MatrixType[] getMatrixTypes() {
        return SharpeningType.values();
    }

    @Override
    public String getName() {
        return "Увеличение резкости";
    }

    @Override
    public String getMatrixTypeName() {
        return "сила увеличения";
    }
}
