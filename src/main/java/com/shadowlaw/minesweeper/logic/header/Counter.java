package com.shadowlaw.minesweeper.logic.header;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Counter {

    private final PropertyChangeSupport observable;
    private List<String> counterState;
    private long value = 0L;
    private final String counterName;

    private static final Logger logger = LogManager.getLogger(Counter.class);

    public Counter(String counterName, String... initialState) {
        this.counterName = counterName;
        List<String> initialStateList = Arrays.asList(initialState);
        logger.debug("creating counter [{}] with values {}", counterName, initialStateList);

        observable = new PropertyChangeSupport(this);;

        counterState = initialStateList;
    }

    public void addCounterDigit(CounterDigit observer) {
        observable.addPropertyChangeListener(observer);
    }


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getCounterName() {
        return counterName;
    }

    public void updateCounterState(Long counterState){
        ArrayList<String> updatedState = new ArrayList<>(Arrays.asList(String.valueOf(counterState).split("(?<=.)")));

        if (this.counterState.size() != updatedState.size() && this.counterState.size() > updatedState.size()) {
            ArrayList<String> paddedList =
                    new ArrayList<>(Collections.nCopies(this.counterState.size() - updatedState.size(), "0"));

            paddedList.addAll(updatedState);
            updatedState = paddedList;
        }

        observable.firePropertyChange("counterState", this.counterState, updatedState);
        this.counterState = updatedState;
    }
}
