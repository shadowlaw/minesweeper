package com.shadowlaw.minesweeper.logic.board;

public class Square {

    private final int row;
    private final int column;

    private final int flatGridLocation;

    private boolean isOpened = false;

    public Square(int row, int column, int flatGridLocation) {
        this.row = row;
        this.column = column;
        this.flatGridLocation = flatGridLocation;
    }

    public boolean isOpened(){
        return this.isOpened;
    }

    public boolean open() {
        isOpened = true;
        return isOpened();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getFlatGridLocation() {
        return flatGridLocation;
    }
}
