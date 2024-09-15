package com.shadowlaw.minesweeper.logic.header;

import com.shadowlaw.minesweeper.ui.components.GameStateIcon;

public class GameState {

    private Boolean isStarted = Boolean.FALSE;
    private boolean isGameStatePlayable = true;
    private Boolean gameWinState = null;
    private GameStateIcon gameStateIcon;

    public GameState(GameStateIcon gameStateIcon) {
        this.gameStateIcon = gameStateIcon;
    }

    public void updateGameState(Boolean gameState) {
        gameWinState = gameState;
        if (!this.gameWinState) {
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
}
