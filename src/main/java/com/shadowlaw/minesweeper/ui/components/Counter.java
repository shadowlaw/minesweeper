package com.shadowlaw.minesweeper.ui.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class Counter extends JPanel {

    private static final Logger logger = LogManager.getLogger(Counter.class);
    private final com.shadowlaw.minesweeper.logic.header.Counter logicCounter;

    public Counter(String counterName, String... initialState) {

        logger.debug("creating counter [{}] with values {}", counterName, initialState);

        setName(counterName);

        GridLayout layout = new GridLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        setLayout(layout);

        com.shadowlaw.minesweeper.logic.header.Counter logicCounter =
                new com.shadowlaw.minesweeper.logic.header.Counter(counterName, initialState);

        this.logicCounter = logicCounter;

        for(int placeIndex = 0; placeIndex < initialState.length; placeIndex++) {
            String placeValue = initialState[placeIndex];

            CounterDigit counterDigit = new CounterDigit(placeValue);
            com.shadowlaw.minesweeper.logic.header.CounterDigit logicDigit =
                    new com.shadowlaw.minesweeper.logic.header.CounterDigit(placeValue, placeIndex, counterDigit);
            logicCounter.addCounterDigit(logicDigit);
            add(counterDigit);
        }
    }

    public com.shadowlaw.minesweeper.logic.header.Counter getLogicCounter() {
        return logicCounter;
    }
}
