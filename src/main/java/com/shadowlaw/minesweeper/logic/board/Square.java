package com.shadowlaw.minesweeper.logic.board;

public class Square {

    private final int row;
    private final int column;

    private final int flatGridLocation;

    private boolean isOpened = false;
    private int adjacentMineNumber = 0;

    public Square(int row, int column, int flatGridLocation) {
        this.row = row;
        this.column = column;
        this.flatGridLocation = flatGridLocation;
    }

    public boolean isOpened(){
        return this.isOpened;
    }

    public void open() {
        isOpened = true;
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

    public int getAdjacentMineNumber() {
        return adjacentMineNumber;
    }

    public void setAdjacentMineNumber(int adjacentMineNumber) {
        this.adjacentMineNumber = adjacentMineNumber;
    }
}
