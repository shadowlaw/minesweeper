package com.shadowlaw.minesweeper.ui;

import com.shadowlaw.minesweeper.logic.LogicManager;
import com.shadowlaw.minesweeper.ui.components.Square;
import com.shadowlaw.minesweeper.ui.constants.Asset;
import com.shadowlaw.minesweeper.ui.listeners.SquareEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.shadowlaw.minesweeper.ui.utils.ImageLabelUtil.createImageLabel;
import static com.shadowlaw.minesweeper.ui.constants.Asset.*;
import static com.shadowlaw.minesweeper.ui.constants.Labels.FLAG_COUNTER;
import static com.shadowlaw.minesweeper.ui.constants.Labels.TIME_COUNTER;
import static com.shadowlaw.minesweeper.ui.constants.Sizes.*;

public class UIManager {

    private static Logger logger = LogManager.getLogger(UIManager.class);

    private static UIManager instance;
    private static LogicManager logicManager = LogicManager.getInstance();

    private final Integer gameBoardSize = 9;
    private final Integer mineNumber = 10;

    private UIManager() {}

    public static UIManager getInstance() {

        if(instance == null) {
            logger.info("creating new ui manager instance");
            instance = new UIManager();
        } else {
            logger.info("returning existing UI manager instance");
        }

        return instance;
    }

    public void setup() {

        JFrame window = createWindow(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle("Minesweeper");

        JPanel headerPanel = getHeaderPanel();
        JPanel boardPanel = getBoardPanel(gameBoardSize);

        window.add(headerPanel);
        window.add(boardPanel);

        window.setResizable(false);
        window.setVisible(true);
    }

    private JPanel getBoardPanel(Integer boardSize) {
        logger.info("creating board panel");
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize, 0, 0));
        boardPanel.setBounds(0, HEADER_PANEL_HEIGHT, BOARD_PANEL_WIDTH, BOARD_PANEL_HEIGHT);

        boardPanel.setBorder(new EmptyBorder(DEFAULT_BOARDER_THICKNESS/2, DEFAULT_BOARDER_THICKNESS, DEFAULT_BOARDER_THICKNESS, DEFAULT_BOARDER_THICKNESS));

        logicManager.createGameBoard(gameBoardSize, mineNumber);

        for (int squareIndex=0; squareIndex < (gameBoardSize * gameBoardSize); squareIndex++) {
            int row = logicManager.getGameGrid().getRowFromGridCellNumber(squareIndex);
            int column = logicManager.getGameGrid().getColumnFromGridCellNumber(squareIndex);

            Square squareLabel = new Square(SQUARE_CLOSED.getPath(), row, column);
            boardPanel.add(squareLabel);

            com.shadowlaw.minesweeper.logic.board.Square square = logicManager.addGameBoardSquare(row, column, squareIndex);
            square.setUiSquare(squareLabel);

        }

        logger.info("board panel created");
        return boardPanel;
    }

    private JPanel getHeaderPanel() {
        logger.info("creating header panel");
        JPanel headerPanel = new JPanel(new BorderLayout(5,5));
        headerPanel.setBounds(0,0, HEADER_PANEL_WIDTH, HEADER_PANEL_HEIGHT);
        headerPanel.setBorder(new EmptyBorder(DEFAULT_BOARDER_THICKNESS, DEFAULT_BOARDER_THICKNESS+5, DEFAULT_BOARDER_THICKNESS/2, DEFAULT_BOARDER_THICKNESS));

        JPanel flagPanel = createCounter(COUNTER_0, COUNTER_1, COUNTER_0, FLAG_COUNTER);
        JPanel counterPanel = createCounter(COUNTER_0, COUNTER_0, COUNTER_0, TIME_COUNTER);

        headerPanel.add(flagPanel, BorderLayout.WEST);
        headerPanel.add(counterPanel, BorderLayout.EAST);
        logger.info("header panel created");
        return headerPanel;
    }

    private JPanel createCounter(Asset hundreds, Asset tens, Asset ones, String counterName) {

        logger.debug("counter [{}] values: 2: {}, 1: {}, 0: {}", counterName, hundreds.name(), tens.name(), ones.name() );
        JPanel panel = new JPanel(new GridLayout(0,3,0,0));

        panel.setName(counterName);
        JLabel counterHundreds = createImageLabel(hundreds.getPath());
        JLabel counterTens = createImageLabel(tens.getPath());
        JLabel counterOnes = createImageLabel(ones.getPath());
        panel.add(counterHundreds);
        panel.add(counterTens);
        panel.add(counterOnes);
        return panel;
    }

    public JFrame createWindow(Integer width, Integer height) {
        logger.debug("window size: {} {}", width, height);
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        return window;
    }

}
