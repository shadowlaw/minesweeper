package com.shadowlaw.minesweeper.ui.constants;

public enum CounterAssets {
    COUNTER_0("sprites/counter_numbers/counter-no-0.png"),
    COUNTER_1("sprites/counter_numbers/counter-no-1.png");

    private final String path;

    CounterAssets(String path) {
        this.path = path;
    }

    public CounterAssets findByValue(Integer value) {
        return null;
    }

    public String getPath() {
        return path;
    }

}
