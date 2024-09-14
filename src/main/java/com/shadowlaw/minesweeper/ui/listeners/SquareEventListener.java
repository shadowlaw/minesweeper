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
        logger.info("square at position {},{} was clicked from button {}", source.getRow(), source.getColumn(), mouseEvent.getButton());

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            logicManager.actionLeftClickOnSquare(source.getRow(), source.getColumn());
        } else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            logicManager.actionRightClickOnSquare(source.getRow(), source.getColumn());
        }
    }

}
