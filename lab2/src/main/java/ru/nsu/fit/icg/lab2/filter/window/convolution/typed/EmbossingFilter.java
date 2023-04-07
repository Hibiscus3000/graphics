package ru.nsu.fit.icg.lab2.filter.window.convolution.typed;

public class EmbossingFilter extends MatrixTypedFilter {

    public enum Direction implements MatrixType {
        NORTH("Север", "north", new int[][]{
                {0, -1, 0},
                {0, 0, 0},
                {0, 1, 0}
        }),
        NORTH_EAST("Северо-восток", "north-east", new int[][]{
                {0, 1, 0},
                {-1, 0, 1},
                {0, -1, 0}
        }),
        EAST("Восток", "east", new int[][]{
                {0, 0, 0},
                {1, 0, -1},
                {0, 0, 0}
        }),
        SOUTH_EAST("Юго-восток", "south-east", new int[][]{
                {0, 1, 0},
                {1, 0, -1},
                {0, -1, 0}
        }),
        SOUTH("Юг", "south", new int[][]{
                {0, 1, 0},
                {0, 0, 0},
                {0, -1, 0}
        }),
        SOUTH_WEST("Юго-запад", "south-west", new int[][]{
                {0, -1, 0},
                {1, 0, -1},
                {0, 1, 0}
        }),
        WEST("Запад", "west", new int[][]{
                {0, 0, 0},
                {-1, 0, 1},
                {0, 0, 0}
        }),
        NORTH_WEST("Северо-запад", "north-west", new int[][]{
                {0, -1, 0},
                {-1, 0, 1},
                {0, 1, 0}
        });

        private final String name;
        private final String imageName;
        private final int[][] matrix;

        Direction(String name, String imageName, int[][] matrix) {
            this.name = name;
            this.imageName = imageName;
            this.matrix = matrix;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getImageName() {
            return imageName;
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
    public MatrixType[] getMatrixTypes() {
        return Direction.values();
    }

    @Override
    public String getName() {
        return "Тиснение";
    }

    @Override
    public String getJsonName() {
        return "embossing";
    }
    //<a target="_blank" href="https://icons8.com/icon/114884/stone-inscription">Stone Inscription</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
