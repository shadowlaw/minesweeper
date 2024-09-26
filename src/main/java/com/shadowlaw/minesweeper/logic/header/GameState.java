package com.shadowlaw.minesweeper.logic.header;

import com.shadowlaw.minesweeper.ui.components.GameStateIcon;

public class GameState {

    private Boolean isStarted = Boolean.FALSE;
    private boolean isGameStatePlayable = true;
    private Boolean gameWinState = null;

    private int safeSquares = 0;


    private final GameStateIcon gameStateIcon;

    public GameState(GameStateIcon gameStateIcon) {
        this.gameStateIcon = gameStateIcon;
    }

    public void updateGameEndState(Boolean gameState) {
        gameWinState = gameState;

        if (gameState == null) {
            gameStateIcon.setInitialIcon();
        }else if (gameWinState) {
            gameStateIcon.setGameWin();
        } else {
            gameStateIcon.setGameLost();
        }
    }

    public boolean isGameStartable() {
        return !isStarted && isGameStatePlayable;
    }

    public void setStarted(Boolean started) {
        isStarted = started;
    }

    public boolean isGameStatePlayable() {
        return isGameStatePlayable;
    }

    public void setGameStatePlayable(boolean gameStatePlayable) {
        isGameStatePlayable = gameStatePlayable;
    }

    public void setSafeSquares(int safeSquares) {
        this.safeSquares = safeSquares;
    }

    public boolean checkGameWinCondition(int numberOfAdjacentSquares) {
        return safeSquares == numberOfAdjacentSquares;
    }
}
