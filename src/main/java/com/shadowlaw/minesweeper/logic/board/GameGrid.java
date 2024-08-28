package com.shadowlaw.minesweeper.logic.board;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameGrid {

    private final Logger logger = LogManager.getLogger(GameGrid.class);

    private final Square[][] gameGrid;

    public GameGrid(int rows, int columns) {
        logger.debug("Initializing game grid with size {} X {}", rows, columns);
        this.gameGrid = new Square[rows][columns];
        logger.info("Game grid initialized");
    }

    public void addSquare(Square square) {
        logger.debug("adding square at cell {}:{} -> {}", square.getRow(), square.getColumn(), square.getFlatGridLocation());
        gameGrid[square.getRow()][square.getColumn()] = square;
        logger.debug("square added at cell {}:{} -> {}", square.getRow(), square.getColumn(), square.getFlatGridLocation());
    }

    public Square[][] getGameGrid() {
        return gameGrid;
    }

    public int getRowFromGridCellNumber(int cellNumber) {
        logger.debug("calculating game square row for cell number {} and grid size {}", cellNumber, gameGrid.length);
        int calculatedRow = (int) Math.floor((double) cellNumber / gameGrid.length);
        logger.debug("row calculated at {}", calculatedRow);

        return calculatedRow;
    }

    public int getColumnFromGridCellNumber(int cellNumber) {
        logger.debug("calculating game square column for cell number {} and grid size {}", cellNumber, gameGrid.length);
        int calculatedColumn = cellNumber % gameGrid.length;
        logger.debug("column calculated at {}", calculatedColumn);

        return calculatedColumn;
    }
}
