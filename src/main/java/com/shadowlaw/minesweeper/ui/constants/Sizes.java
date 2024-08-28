package com.shadowlaw.minesweeper.ui.constants;

public class Sizes {
    public static final Integer WINDOW_WIDTH = 275;
    public static final Integer WINDOW_HEIGHT = 350;
    public static final Integer DEFAULT_BOARDER_THICKNESS = 10;

    public static final Integer HEADER_PANEL_WIDTH = WINDOW_WIDTH - DEFAULT_BOARDER_THICKNESS - 7;
    public static final Integer HEADER_PANEL_HEIGHT = 50;

    public static final Integer BOARD_PANEL_WIDTH = WINDOW_WIDTH - DEFAULT_BOARDER_THICKNESS - 5;
    public static final Integer BOARD_PANEL_HEIGHT = ((WINDOW_HEIGHT - HEADER_PANEL_HEIGHT) - (DEFAULT_BOARDER_THICKNESS*4)) + 5;

}
