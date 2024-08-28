package com.shadowlaw.minesweeper.logic;

import com.shadowlaw.minesweeper.logic.board.GameGrid;
import com.shadowlaw.minesweeper.logic.board.Square;

import javax.swing.*;

public class LogicManager {

    private static LogicManager instance;
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

    public void addGameBoardSquare(JLabel squareLabel, int cellIndex) {
        Square square = new Square(gameGrid.getRowFromGridCellNumber(cellIndex), gameGrid.getColumnFromGridCellNumber(cellIndex), cellIndex);
        gameGrid.addSquare(square);
    }
}
