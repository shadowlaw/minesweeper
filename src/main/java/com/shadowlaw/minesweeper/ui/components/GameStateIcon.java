package com.shadowlaw.minesweeper.ui.components;

import com.shadowlaw.minesweeper.ui.listeners.RestButtonListener;

import static com.shadowlaw.minesweeper.ui.constants.Asset.*;

public class GameStateIcon extends ImageLabel{

    public GameStateIcon() {
        setImage(FACE_SMILE.getPath());

        addMouseListener(new RestButtonListener());
    }

    public void setGameLost() {
        setImage(FACE_CROSS.getPath());
    }

    public void setGameWin() {
        setImage(FACE_COOL.getPath());
    }

    public void setInitialIcon() {
        setImage(FACE_SMILE.getPath());
    }
}
