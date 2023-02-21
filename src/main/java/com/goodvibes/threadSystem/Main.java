package com.goodvibes.threadSystem;

import com.goodvibes.threadSystem.gui.ViewSystem;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("com.goodvibes");
        logger.setLevel(Level.ALL);
        try {
            createScreen(logger);
        } catch (Exception ex) {
            logger.error("Exception Capture. - " + ex.getMessage());
        }
    }

    private static void createScreen(Logger logger) {
        ViewSystem vs = new ViewSystem(logger);
        vs.setContentPane(vs.PanelMain);
        vs.setTitle("System Thread");
        vs.setSize(600, 400);
        vs.setVisible(true);
        vs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
