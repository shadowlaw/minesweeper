package com.shadowlaw.minesweeper.logic;

import com.shadowlaw.minesweeper.logic.board.GameGrid;
import com.shadowlaw.minesweeper.logic.board.Square;
import com.shadowlaw.minesweeper.logic.header.Counter;
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
    private ScheduledExecutorService gameTimer;
    private TimerCounterTask timerCounterTask;
    private Counter flagCounter;

    private LogicManager() {}

    public static LogicManager getInstance(){
        if (instance == null){
            instance = new LogicManager();
        }

        return instance;
    }

    public void createGameBoard(int boardSize, int mineNumber, int flagLimit) {
        gameGrid = new GameGrid(boardSize, boardSize, mineNumber, flagLimit);
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

    public void startGame(int startSquareRow, int startSquareColumn, boolean flagSquare) {

        logger.info("starting new game from square {}:{}", startSquareRow, startSquareColumn);

        isStarted = true;

        gameGrid.initialize(startSquareRow, startSquareColumn);

        startGameTimer(gameTimerInitialDelay, gameTimerDelayPeriod, TimeUnit.MILLISECONDS);

        if (!flagSquare) {
            gameGrid.openSquare(startSquareRow, startSquareColumn);
        }

        logger.info("game started");

    }

    public void setTimerCounterTask(TimerCounterTask timerCounterTask) {
        this.timerCounterTask = timerCounterTask;
    }

    private void startGameTimer(long initialDelay, long period, TimeUnit timeUnit) {
        gameTimer = Executors.newSingleThreadScheduledExecutor();
        gameTimer.scheduleAtFixedRate(timerCounterTask, initialDelay, period, timeUnit);
    }

    public boolean isGameInitialized() {
        return isGameinItialized;
    }

    public void actionLeftClickOnSquare(int row, int column) {
        gameGrid.openSquare(row, column);

        Square square = gameGrid.getSquare(row, column);

        if (square.isOpened() && square.isMine()) {
            logger.info("Mine square {}:{} opened", row, column);
            endGame();
        }

        flagCounter.updateCounterState((long) gameGrid.getAvailableFlags());
    }

    public void actionRightClickOnSquare(int row, int column) {
        gameGrid.toggleSquareFlag(row, column);
        flagCounter.updateCounterState((long) gameGrid.getAvailableFlags());
    }

    public void setFlagCounter(Counter logicCounter) {
        this.flagCounter = logicCounter;
    }

    private void endGame() {
        logger.info("Ending Game");
        stopGameTimer();
        isStarted = false;
        logger.info("Game ended");
    }

    private void stopGameTimer() {

        if (gameTimer.isShutdown()) {
            logger.warn("game timer is already stopped");
            return;
        }

        gameTimer.shutdown();

        try{
            while (!gameTimer.awaitTermination(gameTimerDelayPeriod, TimeUnit.MILLISECONDS)) {
                logger.debug("waiting for game timer to terminate. waiting for {} milliseconds", gameTimerDelayPeriod);
            }
        } catch (InterruptedException e) {
            logger.trace(e);
            logger.error("Game timer termination Interrupted");
            return;
        }
    }
}
