package com.shadowlaw.minesweeper.logic.header;

import com.shadowlaw.minesweeper.ui.components.Counter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public class TimerCounterTask extends TimerTask {

    private final Logger logger = LogManager.getLogger(TimerCounterTask.class);

    private final Counter counter;

    public TimerCounterTask(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        logger.info("Timer tick for counter: {} at {}", counter.getName(), counter.getValue());
        counter.updateCounterState(counter.getValue() );
        counter.setValue(counter.getValue() + 1L);
    }

}
