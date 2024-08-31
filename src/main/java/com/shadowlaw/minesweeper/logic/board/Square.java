package com.shadowlaw.minesweeper.logic.board;

public class Square {

    private final Position position;

    private final int flatGridLocation;

    private boolean isOpened = false;
    private int adjacentMineNumber = 0;

    public Square(int row, int column, int flatGridLocation) {
        position = new Position(row, column);
        this.flatGridLocation = flatGridLocation;
    }

    public boolean isOpened(){
        return this.isOpened;
    }

    public void open() {
        isOpened = true;
    }

    public Position getPosition() {
        return position;
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
