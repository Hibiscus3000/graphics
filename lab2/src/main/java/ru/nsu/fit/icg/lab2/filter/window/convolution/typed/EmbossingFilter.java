package ru.nsu.fit.icg.lab2.filter.window.convolution.typed;

public class EmbossingFilter extends MatrixTypedFilter {

    public enum Direction implements MatrixType {
        NORTH("Север", new int[][]{
                {0, -1, 0},
                {0, 0, 0},
                {0, 1, 0}
        }),
        EAST("Восток", new int[][]{
                {0, 0, 0},
                {1, 0, -1},
                {0, 0, 0}
        }),
        SOUTH("Юг", new int[][]{
                {0, 1, 0},
                {0, 0, 0},
                {0, -1, 0}
        }),
        WEST("Запад", new int[][]{
                {0, 0, 0},
                {-1, 0, 1},
                {0, 0, 0}
        }),
        NORTH_EAST("Северо-восток", new int[][]{
                {0, 1, 0},
                {-1, 0, 1},
                {0, -1, 0}
        }),
        SOUTH_EAST("Юго-восток", new int[][]{
                {0, 1, 0},
                {1, 0, -1},
                {0, -1, 0}
        }),
        SOUTH_WEST("Юго-запад", new int[][]{
                {0, -1, 0},
                {1, 0, -1},
                {0, 1, 0}
        }),
        NORTH_WEST("Северо-запад", new int[][]{
                {0, -1, 0},
                {-1, 0, 1},
                {0, 1, 0}
        });

        private final String name;
        private final int[][] matrix;

        Direction(String name, int[][] matrix) {
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

    public EmbossingFilter() {
        super(Direction.NORTH_EAST);
        addition = 128;
    }

    @Override
    public String getMatrixTypeName() {
        return "направление тиснения";
    }

    @Override
    public MatrixType[] getMatrixTypes() {
        return Direction.values();
    }

    @Override
    public String getName() {
        return "Тиснение";
    }
}
