package ru.nsu.fit.icg.lab2.filter.window.convolution;

public class EmbossingFilter extends ConvolutionFilter {

    public enum Direction {
        NORTH("Север", new Integer[][]{
                {null, -1, null},
                {null, null, null},
                {null, 1, null}
        }),
        EAST("Восток", new Integer[][]{
                {null, null, null},
                {1, null, -1},
                {null, null, null}
        }),
        SOUTH("Юг", new Integer[][]{
                {null, 1, null},
                {null, null, null},
                {null, -1, null}
        }),
        WEST("Запад", new Integer[][]{
                {null, null, null},
                {-1, null, 1},
                {null, null, null}
        }),
        NORTH_EAST("Северо-восток", new Integer[][]{
                {null, 1, null},
                {-1, null, 1},
                {null, -1, null}
        }),
        SOUTH_EAST("Юго-восток", new Integer[][]{
                {null, 1, null},
                {1, null, -1},
                {null, -1, null}
        }),
        SOUTH_WEST("Юго-запад", new Integer[][]{
                {null, -1, null},
                {1, null, -1},
                {null, 1, null}
        }),
        NORTH_WEST("Северо-запад", new Integer[][]{
                {null, -1, null},
                {-1, null, 1},
                {null, 1, null}
        });

        private final String name;
        private final Integer[][] matrix;

        Direction(String name, Integer[][] matrix) {
            this.name = name;
            this.matrix = matrix;
        }

        public String getName() {
            return name;
        }

        public Integer[][] getMatrix() {
            return matrix;
        }
    }

    public EmbossingFilter() {
        setDirection(direction);
        divider = 1;
        addition = 128;
    }

    private Direction direction = Direction.NORTH_EAST;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        matrix = direction.getMatrix();
    }

    @Override
    public String getName() {
        return "Тиснение";
    }

    @Override
    public String getJsonName() {
        return "embossing";
    }
}
