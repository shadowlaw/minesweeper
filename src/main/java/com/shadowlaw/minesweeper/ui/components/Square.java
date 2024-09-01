package com.shadowlaw.minesweeper.ui.components;

import com.shadowlaw.minesweeper.ui.constants.Asset;
import com.shadowlaw.minesweeper.ui.listeners.SquareEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

import static com.shadowlaw.minesweeper.ui.constants.Asset.*;

public class Square extends JLabel {

    private final Logger logger = LogManager.getLogger(Square.class);

    private String path;
    private final int row;
    private final int column;
    private boolean isMine = false;

    public Square(int row, int column) {
        this.row = row;
        this.column = column;
        setBorder(new EmptyBorder(0,0,0,0));
        addMouseListener(new SquareEventListener());
    }

    public Square(String path, int row, int column) {
        this(row, column);
        this.path = path;
        setImage(path);
    }

    public void setImage(String path) {
        URL url = getResource(path);
        if (url == null){
            logger.error("Unable to locate access path: {}", path);
            return;
        }
        setIcon(new ImageIcon(url));
        this.path = path;
    }

    private URL getResource(String path) {
        logger.debug("retrieving URL from path {}", path);
        return Square.class.getClassLoader().getResource(path);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void open(){
        setImage(path);
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        setPath(Asset.SQUARE_MINE_REVEALED.getPath());
        isMine = mine;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setAdjacentMineValue(int adjacentMineValue) {
        String imagePath;
        switch(adjacentMineValue) {
            case 1:
                imagePath = NUMBER_1.getPath();
                break;
            case 2:
                imagePath = NUMBER_2.getPath();
                break;
            case 3:
                imagePath = NUMBER_3.getPath();
                break;
            case 4:
                imagePath = NUMBER_4.getPath();
                break;
            case 5:
                imagePath = NUMBER_5.getPath();
                break;
            case 6:
                imagePath = NUMBER_6.getPath();
                break;
            case 7:
                imagePath = NUMBER_7.getPath();
                break;
            case 8:
                imagePath = NUMBER_8.getPath();
                break;
            default:
                imagePath = SQUARE_OPENED.getPath();
                break;
        }
        path = imagePath;
    }
}
