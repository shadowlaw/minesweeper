package com.shadowlaw.minesweeper.logic;

import com.shadowlaw.minesweeper.logic.board.GameGrid;
import com.shadowlaw.minesweeper.logic.board.Square;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class LogicManager {

    private static LogicManager instance;
    private final Logger logger = LogManager.getLogger(LogicManager.class);

    private Boolean isStarted = Boolean.FALSE;
    private GameGrid gameGrid;

    private LogicManager() {}

    public static LogicManager getInstance(){
        if (instance == null){
            instance = new LogicManager();
        }

        return instance;
    }

    public void createGameBoard(int boardSize) {
        gameGrid = new GameGrid(boardSize, boardSize);
    }

    public Square addGameBoardSquare(int cellIndex) {
        Square square = new Square(gameGrid.getRowFromGridCellNumber(cellIndex), gameGrid.getColumnFromGridCellNumber(cellIndex), cellIndex);
        gameGrid.addSquare(square);
        return square;
    }

    public boolean isGameStarted() {
        return isStarted;
    }

    public void startGame(int startSquareRow, int startSquareColumn) {

        logger.info("starting new game from square {}:{}", startSquareRow, startSquareColumn);
        isStarted = true;
        Square square = gameGrid.getSquare(startSquareRow, startSquareColumn);
        square.open();

        logger.info("game started");

    }
}
