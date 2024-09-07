package com.shadowlaw.minesweeper.ui.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.net.URL;

public abstract class ImageLabel extends JLabel {

    private final Logger logger = LogManager.getLogger(ImageLabel.class);

    protected String path;

    protected void setImage(String path) {
        URL url = getResource(path);
        if (url == null){
            logger.error("Unable to locate access path: {}", path);
            return;
        }
        setIcon(new ImageIcon(url));
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    protected URL getResource(String path) {
        logger.debug("retrieving URL from path {}", path);
        return Square.class.getClassLoader().getResource(path);
    }
}
