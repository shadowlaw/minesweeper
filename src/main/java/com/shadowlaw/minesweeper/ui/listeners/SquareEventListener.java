package com.shadowlaw.minesweeper.ui.listeners;

import com.shadowlaw.minesweeper.logic.LogicManager;
import com.shadowlaw.minesweeper.ui.components.Square;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.shadowlaw.minesweeper.ui.constants.Asset.SQUARE_OPENED;


public class SquareEventListener extends MouseAdapter {

    private final Logger logger = LogManager.getLogger(SquareEventListener.class);
    private final LogicManager logicManager = LogicManager.getInstance();

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            executeLeftClickAction(mouseEvent);
        }
    }

    private void executeLeftClickAction (MouseEvent mouseEvent) {
        Square source = (Square) mouseEvent.getSource();
        logger.info("square at position {},{} was clicked", source.getRow(), source.getColumn());

        if(!logicManager.isGameStarted()) {
            startGame(source);
        }

        logicManager.openSquare(source.getRow(), source.getColumn());
    }

    private void startGame(Square square){

        logicManager.startGame(square.getRow(), square.getColumn());
    }

}
