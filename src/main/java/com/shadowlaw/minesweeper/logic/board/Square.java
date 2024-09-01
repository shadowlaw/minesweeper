package com.shadowlaw.minesweeper.logic.board;

public class Square {

    private final Position position;
    private final int flatGridLocation;
    private com.shadowlaw.minesweeper.ui.components.Square uiSquare;

    private boolean isOpened = false;
    private boolean isMine = false;
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
        uiSquare.open();
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
        if (isMine()) {
            return;
        }
        uiSquare.setAdjacentMineValue(adjacentMineNumber);
        this.adjacentMineNumber = adjacentMineNumber;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
        uiSquare.setMine(mine);
    }

    public com.shadowlaw.minesweeper.ui.components.Square getUiSquare() {
        return uiSquare;
    }

    public void setUiSquare(com.shadowlaw.minesweeper.ui.components.Square uiSquare) {
        this.uiSquare = uiSquare;
    }
}
