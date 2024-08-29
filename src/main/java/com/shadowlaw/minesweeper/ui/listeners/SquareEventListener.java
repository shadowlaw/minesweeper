package com.shadowlaw.minesweeper.ui.listeners;

import com.shadowlaw.minesweeper.logic.LogicManager;
import com.shadowlaw.minesweeper.ui.utils.ImageLabelUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.shadowlaw.minesweeper.ui.constants.Asset.SQUARE_OPENED;

public class SquareEventListener extends MouseAdapter {

    private final Logger logger = LogManager.getLogger(SquareEventListener.class);
    private final LogicManager logicManager = LogicManager.getInstance();

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        * 1. Check logic manager if game was already started
        * 2. if not started then run start game
        **/

        JLabel source = (JLabel) e.getSource();
        logger.info("square at position {} was clicked", source.getName());
        int eventSquareRow = Integer.parseInt(source.getName().split(",")[0]);
        int eventSquareColumn = Integer.parseInt(source.getName().split(",")[1]);

        if(!logicManager.isGameStarted()) {
            startGame(source, eventSquareRow, eventSquareColumn);
        }
    }

    private void startGame(JLabel squareSource, int startSquareRow, int startSquareColumn){

        ImageLabelUtil.setImageIcon(squareSource, SQUARE_OPENED.getPath());
        logicManager.startGame(startSquareRow, startSquareColumn);
    }

}
