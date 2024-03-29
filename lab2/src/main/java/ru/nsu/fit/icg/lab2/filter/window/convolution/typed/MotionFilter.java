package ru.nsu.fit.icg.lab2.filter.window.convolution.typed;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;
import java.util.Map;

public class MotionFilter extends MatrixTypedFilter {

    public enum Direction implements MatrixType {
        MAIN_DIAGONAL("По главной диагонали", "north-west"),
        VERTICAL("Вертикальное направление", "north"),
        SECONDARY_DIAGONAL("По побочной диагонали", "north-east"),
        HORIZONTAL("Горизонтальное направление", "east");

        private final String name;
        private final String imageName;
        protected int[][] matrix;

        Direction(String name, String imageName) {
            this.name = name;
            this.imageName = imageName;
        }

        @Override
        public String getName() {
            return name;
        }


        @Override
        public int[][] getMatrix() {
            return MotionFilter.getMatrix(this);
        }

        @Override
        public String getImageName() {
            return imageName;
        }
    }

    private static final Map<Direction, Map<Integer, int[][]>> matricesMap = new HashMap<>();

    private static int[][] getMatrix(Direction direction) {
        matricesMap.computeIfAbsent(direction, k -> new HashMap<>());
        Map<Integer, int[][]> directionMap = matricesMap.get(direction);
        int windowSide = windowSideProperty.get();
        directionMap.computeIfAbsent(windowSide, k -> buildMatrix(direction));
        return directionMap.get(windowSide);
    }

    private static int[][] buildMatrix(Direction direction) {
        int windowSide = windowSideProperty.get();
        int[][] matrix = new int[windowSide][windowSide];
        switch (direction) {
            case HORIZONTAL -> {
                int middleLine = windowSide / 2;
                for (int i = 0; i < windowSide; ++i) {
                    for (int j = 0; j < windowSide; ++j) {
                        if (j == middleLine) {
                            matrix[i][j] = 1;
                        } else {
                            matrix[i][j] = 0;
                        }
                    }
                }
            }
            case VERTICAL -> {
                int middleLine = windowSide / 2;
                for (int i = 0; i < windowSide; ++i) {
                    for (int j = 0; j < windowSide; ++j) {
                        if (i == middleLine) {
                            matrix[i][j] = 1;
                        } else {
                            matrix[i][j] = 0;
                        }
                    }
                }
            }
            case MAIN_DIAGONAL -> {
                for (int i = 0; i < windowSide; ++i) {
                    for (int j = 0; j < windowSide; ++j) {
                        if (i == j) {
                            matrix[i][j] = 1;
                        } else {
                            matrix[i][j] = 0;
                        }
                    }
                }
            }
            case SECONDARY_DIAGONAL -> {
                for (int i = 0; i < windowSide; ++i) {
                    for (int j = 0; j < windowSide; ++j) {
                        if (i + j + 1 == windowSide) {
                            matrix[i][j] = 1;
                        } else {
                            matrix[i][j] = 0;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private final int maxWindowSide = 23;
    private static final SimpleIntegerProperty windowSideProperty = new SimpleIntegerProperty(9);

    public MotionFilter() {
        super(Direction.HORIZONTAL);
        divider = windowSideProperty.get();
        windowSideProperty.addListener((observable, newVal, oldVal) -> {
            int newValInt = (int) newVal;
            if (0 == newValInt % 2) {
                windowSideProperty.set(Math.min(newValInt + 1, maxWindowSide));
            } else {
                divider = newValInt;
            }
        });
    }

    public IntegerProperty windowSideProperty() {
        return windowSideProperty;
    }

    public int getMaxWindowSide() {
        return maxWindowSide;
    }

    @Override
    public MatrixType[] getMatrixTypes() {
        return Direction.values();
    }

    @Override
    public String getName() {
        return "Фильтр движения";
    }

    @Override
    public String getJsonName() {
        return "motion";
    }
    //<a target="_blank" href="https://icons8.com/icon/uvfFzgUxTCc0/right-arrow">Right Arrow</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
}
