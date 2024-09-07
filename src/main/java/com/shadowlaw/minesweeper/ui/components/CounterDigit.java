package com.shadowlaw.minesweeper.ui.components;

import static com.shadowlaw.minesweeper.ui.constants.Asset.*;

public class CounterDigit extends ImageLabel {


    public CounterDigit(String value) {
        setValue(value);
    }

    @Override
    public void setPath(String path) {
        this.path = path;
        setImage(path);
    }

    public void setValue(String value) {
        String imagePath;
        switch(value) {
            case "0":
                imagePath = COUNTER_0.getPath();
                break;
            case "1":
                imagePath = COUNTER_1.getPath();
                break;
            case "2":
                imagePath = COUNTER_2.getPath();
                break;
            case "3":
                imagePath = COUNTER_3.getPath();
                break;
            case "4":
                imagePath = COUNTER_4.getPath();
                break;
            case "5":
                imagePath = COUNTER_5.getPath();
                break;
            case "6":
                imagePath = COUNTER_6.getPath();
                break;
            case "7":
                imagePath = COUNTER_7.getPath();
                break;
            case "8":
                imagePath = COUNTER_8.getPath();
                break;
            case "9":
                imagePath = COUNTER_9.getPath();
                break;
            case "-":
                imagePath = COUNTER_DASH.getPath();
                break;
            default:
                imagePath = COUNTER_BLANK.getPath();
                break;
        }
        setPath(imagePath);
    }
}
