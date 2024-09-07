package com.shadowlaw.minesweeper.logic.header;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CounterDigit implements PropertyChangeListener {

    private final int place;
    private String value;
    private final com.shadowlaw.minesweeper.ui.components.CounterDigit uiCounterDigit;

    public CounterDigit(String value, int place, com.shadowlaw.minesweeper.ui.components.CounterDigit uiCounterDigit) {
        this.place = place;
        this.uiCounterDigit = uiCounterDigit;
        setValue(value);
    }

    public void setValue(String value) {
        this.value = value;
        uiCounterDigit.setValue(value);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<String> currentState = (List<String>) evt.getOldValue();
        List<String> updatedState = (List<String>) evt.getNewValue();

        if (!currentState.get(place).equals(updatedState.get(place))){
            setValue(updatedState.get(place));
        }

    }
}
