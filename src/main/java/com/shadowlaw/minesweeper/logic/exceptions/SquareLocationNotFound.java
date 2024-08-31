package com.shadowlaw.minesweeper.logic.exceptions;

public class SquareLocationNotFound extends RuntimeException {
    public SquareLocationNotFound(String message) {
        super(message);
    }
}
