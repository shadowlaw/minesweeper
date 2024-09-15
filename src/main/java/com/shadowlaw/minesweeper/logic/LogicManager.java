package com.shadowlaw.minesweeper.logic;

import com.shadowlaw.minesweeper.logic.board.GameGrid;
import com.shadowlaw.minesweeper.logic.board.Square;
import com.shadowlaw.minesweeper.logic.header.Counter;
import com.shadowlaw.minesweeper.logic.header.GameState;
import com.shadowlaw.minesweeper.logic.header.TimerCounterTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class LogicManager {

    private static LogicManager instance;
    private final Logger logger = LogManager.getLogger(LogicManager.class);

    private GameState gameState;

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

    public GameGrid getGameGrid() {
        return gameGrid;
    }

    public void startGame(int startSquareRow, int startSquareColumn, boolean flagSquare) {

        logger.info("starting new game from square {}:{}", startSquareRow, startSquareColumn);

        gameGrid.initialize(startSquareRow, startSquareColumn);

        startGameTimer(gameTimerInitialDelay, gameTimerDelayPeriod, TimeUnit.MILLISECONDS);

        if (!flagSquare) {
            gameGrid.openSquare(startSquareRow, startSquareColumn);
        }

        gameState.setStarted(true);

        logger.info("game started");

    }

    public void setTimerCounterTask(TimerCounterTask timerCounterTask) {
        this.timerCounterTask = timerCounterTask;
    }

    private void startGameTimer(long initialDelay, long period, TimeUnit timeUnit) {
        gameTimer = Executors.newSingleThreadScheduledExecutor();
        gameTimer.scheduleAtFixedRate(timerCounterTask, initialDelay, period, timeUnit);
    }


    public void actionLeftClickOnSquare(int row, int column) {

        if(gameState.isGameStartable()) {
            startGame(row, column, false);
        }

        if (!gameState.isGameStatePlayable()) {
            logger.warn("game is not in playable position");
            return;
        }

        gameGrid.openSquare(row, column);

        Square square = gameGrid.getSquare(row, column);

        if (square.isOpened() && square.isMine()) {
            logger.info("Mine square {}:{} opened", row, column);
            gameState.updateGameEndState(false);
            endGame();
            return;
        }

        gameState.setSafeSquares(gameGrid.getNumberOfAdjacentMineSquaresOpened());
        flagCounter.updateCounterState((long) gameGrid.getAvailableFlags());

        if (gameState.checkGameWinCondition(gameGrid.getSquaresWithAdjacentMines())) {
            logger.info("Player won the game");
            gameGrid.flagAllMineSquares();
            gameState.updateGameEndState(true);
            endGame();
        }

    }

    public void actionRightClickOnSquare(int row, int column) {
        if(gameState.isGameStartable()) {
            startGame(row, column, true);
        }

        if (!gameState.isGameStatePlayable()) {
            logger.warn("game is not in playable position");
            return;
        }

        gameGrid.toggleSquareFlag(row, column);
        flagCounter.updateCounterState((long) gameGrid.getAvailableFlags());
    }

    public void setFlagCounter(Counter logicCounter) {
        this.flagCounter = logicCounter;
    }

    private void endGame() {
        logger.info("ending Game");
        stopGameTimer();
        gameState.setStarted(false);
        gameState.setGameStatePlayable(false);
        logger.info("game ended");
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
        }
    }

    public void initializeGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
