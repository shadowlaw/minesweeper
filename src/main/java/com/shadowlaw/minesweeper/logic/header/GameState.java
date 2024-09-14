package com.shadowlaw.minesweeper.logic.header;

import com.shadowlaw.minesweeper.ui.components.GameStateIcon;

public class GameState {

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


}
