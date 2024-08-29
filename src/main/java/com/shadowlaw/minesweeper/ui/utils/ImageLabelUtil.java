package com.shadowlaw.minesweeper.ui.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

public class ImageLabelUtil {

    private final static Logger logger = LogManager.getLogger(ImageLabelUtil.class);

    private ImageLabelUtil() {}

    public static JLabel createImageLabel(String path) {
        try{
            logger.debug("creating image label from path {}", path);
            ImageIcon imageIcon = new ImageIcon(getResource(path));
            JLabel label = new JLabel(imageIcon);
            label.setBorder(new EmptyBorder(0,0,0,0));
            return label;
        } catch (Exception e) {
            logger.error("{}: {}", e.getClass(), e.getMessage());
            logger.trace(e.getStackTrace());
            return new JLabel();
        }
    }

    public static void setImageIcon(JLabel imageLabel, String path) {
        try{
            imageLabel.setIcon(new ImageIcon(getResource(path)));
        } catch (Exception e) {
            logger.error("{}: {}", e.getClass(), e.getMessage());
            logger.trace(e.getStackTrace());
        }
    }

    private static URL getResource(String path) {
        return ImageLabelUtil.class.getClassLoader().getResource(path);
    }
}
