package com.shadowlaw.minesweeper.ui.listeners;

import com.shadowlaw.minesweeper.logic.LogicManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RestButtonListener extends MouseAdapter {

    private final Logger logger = LogManager.getLogger(RestButtonListener.class);
    private final LogicManager logicManager = LogicManager.getInstance();

    @Override
    public void mouseClicked(MouseEvent event) {
        logger.info("game reset triggered");

        logicManager.resetGame();
    }
}
