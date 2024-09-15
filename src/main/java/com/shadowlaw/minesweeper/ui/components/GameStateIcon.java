package com.shadowlaw.minesweeper.ui.components;

import static com.shadowlaw.minesweeper.ui.constants.Asset.FACE_CROSS;
import static com.shadowlaw.minesweeper.ui.constants.Asset.FACE_SMILE;

public class GameStateIcon extends ImageLabel{

    public GameStateIcon() {
        setImage(FACE_SMILE.getPath());
    }

    public void setGameLost() {
        setImage(FACE_CROSS.getPath());
    }
}
