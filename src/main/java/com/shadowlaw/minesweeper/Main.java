package com.shadowlaw.minesweeper;

import com.shadowlaw.minesweeper.ui.UIManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    private static final UIManager gameUiManager = UIManager.getInstance();

    public static void main(String[] args) {
        gameUiManager.setup();
    }
}