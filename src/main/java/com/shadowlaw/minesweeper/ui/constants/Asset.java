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

    NUMBER_1("sprites/squares/square-number-1.png"),
    NUMBER_2("sprites/squares/square-number-2.png"),
    NUMBER_3("sprites/squares/square-number-3.png"),
    NUMBER_4("sprites/squares/square-number-4.png"),
    NUMBER_5("sprites/squares/square-number-5.png"),
    NUMBER_6("sprites/squares/square-number-6.png"),
    NUMBER_7("sprites/squares/square-number-7.png"),
    NUMBER_8("sprites/squares/square-number-8.png"),

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
