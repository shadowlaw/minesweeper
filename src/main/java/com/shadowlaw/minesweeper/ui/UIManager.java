package com.shadowlaw.minesweeper.ui;

import static com.shadowlaw.minesweeper.ui.constants.CounterAssets.COUNTER_0;
import static com.shadowlaw.minesweeper.ui.constants.CounterAssets.COUNTER_1;

import com.shadowlaw.minesweeper.ui.constants.CounterAssets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIManager {

    private static Logger logger = LogManager.getLogger(UIManager.class);

    private static UIManager instance;

    private UIManager() {}

    public static UIManager getInstance() {

        if(instance == null) {
            logger.info("creating new ui manager instance");
            instance = new UIManager();
        } else {
            logger.info("returning existing UI manager instance");
        }

        return instance;
    }

    public void setup() {

        JFrame window = createWindow(500, 600);

        JPanel headerPanel = getHeaderPanel();

        window.add(headerPanel);
        window.setVisible(true);
    }

    private JPanel getHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(0,5));
        headerPanel.setBounds(0,0, 484, 80);
        headerPanel.setBorder(new EmptyBorder(10, 10, 10 ,10));

//        headerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 10));

        JPanel flagPanel = createCounter(COUNTER_0, COUNTER_1, COUNTER_0);
        JPanel counterPanel = createCounter(COUNTER_0, COUNTER_0, COUNTER_0);

        headerPanel.add(flagPanel, BorderLayout.WEST);
        headerPanel.add(counterPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createCounter(CounterAssets hundreds, CounterAssets tens, CounterAssets ones) {
        JPanel panel = new JPanel(new GridLayout(0,3,0,0));
//        panel.setBackground(Color.black);

        JLabel counterHundreds = createImageLabel(hundreds.getPath());
        JLabel counterTens = createImageLabel(tens.getPath());
        JLabel counterOnes = createImageLabel(ones.getPath());
        panel.add(counterHundreds);
        panel.add(counterTens);
        panel.add(counterOnes);
        return panel;
    }

    public JFrame createWindow(Integer width, Integer height) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(width, height);
        window.setLayout(null);
        window.setLocationRelativeTo(null);

        return window;
    }

    public JLabel createImageLabel(String path) {
        try{
            ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource(path));
            return new JLabel(imageIcon);
        } catch (Exception e) {
            logger.error("{}: {}", e.getClass(), e.getMessage());
            logger.trace(e.getStackTrace());
            return new JLabel();
        }
    }

}
