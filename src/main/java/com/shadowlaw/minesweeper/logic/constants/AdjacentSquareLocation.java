package com.shadowlaw.minesweeper.logic.constants;

public enum AdjacentSquareLocation {
    LEFT(0, -1),
    RIGHT(0, 1),
    ABOVE(-1, 0),
    BELOW(+1, 0),
    LEFT_UPPER_DIAGONAL(-1, -1),
    LEFT_LOWER_DIAGONAL(1   , -1),
    RIGHT_UPPER_DIAGONAL(-1, 1),
    RIGHT_LOWER_DIAGONAL(1, 1);

    private final int rowOffset;
    private final int columnOffset;

    AdjacentSquareLocation(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public int getRowOfInterest(int row) {
        return row + rowOffset;
    }
    public int getColumnOfInterest(int column) {
        return column + columnOffset;
    }
}
