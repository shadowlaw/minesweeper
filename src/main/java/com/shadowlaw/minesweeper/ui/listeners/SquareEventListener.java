package com.shadowlaw.minesweeper.ui.listeners;

import com.shadowlaw.minesweeper.logic.LogicManager;
import com.shadowlaw.minesweeper.ui.components.Square;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SquareEventListener extends MouseAdapter {

    private final Logger logger = LogManager.getLogger(SquareEventListener.class);
    private final LogicManager logicManager = LogicManager.getInstance();

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        Square source = (Square) mouseEvent.getSource();
        logger.info("square at position {},{} was clicked", source.getRow(), source.getColumn());

        if(!logicManager.isGameStarted()) {
            startGame(source.getRow(), source.getColumn());
        }
    }

    private void startGame(int startSquareRow, int startSquareColumn){

        logicManager.startGame(startSquareRow, startSquareColumn);
    }

}
