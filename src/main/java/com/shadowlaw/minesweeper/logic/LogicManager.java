package com.shadowlaw.minesweeper.logic;

import com.shadowlaw.minesweeper.logic.board.GameGrid;
import com.shadowlaw.minesweeper.logic.board.Square;
import com.shadowlaw.minesweeper.logic.header.TimerCounterTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class LogicManager {

    private static LogicManager instance;
    private final Logger logger = LogManager.getLogger(LogicManager.class);

    private Boolean isStarted = Boolean.FALSE;
    private GameGrid gameGrid;

    private final long gameTimerInitialDelay = 1000L;
    private final long gameTimerDelayPeriod = 1000L;
    private TimerCounterTask timerCounterTask;

    private LogicManager() {}

    public static LogicManager getInstance(){
        if (instance == null){
            instance = new LogicManager();
        }

        return instance;
    }

    public void createGameBoard(int boardSize, int mineNumber) {
        gameGrid = new GameGrid(boardSize, boardSize, mineNumber);
    }

    public Square addGameBoardSquare(int row, int column, int cellIndex) {
        Square square = new Square(row, column, cellIndex);
        gameGrid.addSquare(square);
        return square;
    }

    public boolean isGameStarted() {
        return isStarted;
    }

    public GameGrid getGameGrid() {
        return gameGrid;
    }

    public void startGame(int startSquareRow, int startSquareColumn) {

        logger.info("starting new game from square {}:{}", startSquareRow, startSquareColumn);

        isStarted = true;
        Square square = gameGrid.getSquare(startSquareRow, startSquareColumn);
        gameGrid.initialize(startSquareRow, startSquareColumn);

        startGameTimer(gameTimerInitialDelay, gameTimerDelayPeriod, TimeUnit.MILLISECONDS);

        square.open();

        logger.info("game started");

    }

    public void setTimerCounterTask(TimerCounterTask timerCounterTask) {
        this.timerCounterTask = timerCounterTask;
    }

    private void startGameTimer(long initialDelay, long period, TimeUnit timeUnit) {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(timerCounterTask, initialDelay, period, timeUnit);
    }

    public void openSquare(int row, int column) {
        gameGrid.openSquare(row, column);
    }
}
