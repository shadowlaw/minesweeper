package com.shadowlaw.minesweeper.ui.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Counter extends JPanel {

    private final PropertyChangeSupport observable;
    private List<String> counterState;
    private long value = 0L;

    private static final Logger logger = LogManager.getLogger(Counter.class);

    public Counter(String counterName, String... initialState) {
        observable = new PropertyChangeSupport(this);

        List<String> initialStateList = Arrays.asList(initialState);
        logger.debug("creating counter [{}] with values {}", counterName, initialStateList);

        setName(counterName);

        counterState = initialStateList;

        GridLayout layout = new GridLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        setLayout(layout);

        for(int placeIndex = 0; placeIndex < initialState.length; placeIndex++) {
            CounterDigit counterDigit = new CounterDigit(initialStateList.get(placeIndex), placeIndex);
            observable.addPropertyChangeListener(counterDigit);
            add(counterDigit);
        }
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
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
