package com.shadowlaw.minesweeper.ui.constants;

public enum Asset {
    COUNTER_0("sprites/counter_numbers/counter-no-0.png"),
    COUNTER_1("sprites/counter_numbers/counter-no-1.png"),
    COUNTER_2("sprites/counter_numbers/counter-no-2.png"),
    COUNTER_3("sprites/counter_numbers/counter-no-3.png"),
    COUNTER_4("sprites/counter_numbers/counter-no-4.png"),
    COUNTER_5("sprites/counter_numbers/counter-no-5.png"),
    COUNTER_6("sprites/counter_numbers/counter-no-6.png"),
    COUNTER_7("sprites/counter_numbers/counter-no-7.png"),
    COUNTER_8("sprites/counter_numbers/counter-no-8.png"),
    COUNTER_9("sprites/counter_numbers/counter-no-9.png"),

    SQUARE_CLOSED("sprites/squares/square-closed.png"),
    SQUARE_OPENED("sprites/squares/square-opened.png"),
    SQUARE_MINE_REVEALED("sprites/squares/square-bomb-revealed.png"),
    SQUARE_MINE_REVEALED_PRESSED("sprites/squares/square-bomb-revealed-pressed.png");

    private final String path;

    Asset(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
