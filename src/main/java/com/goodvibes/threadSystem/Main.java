package com.goodvibes.threadSystem;

import com.goodvibes.threadSystem.gui.ViewSystem;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger("com.goodvibes");
            logger.setLevel(Level.ALL);

            ViewSystem vs = new ViewSystem(logger);
            vs.setContentPane(vs.PanelMain);
            vs.setTitle("System Thread");
            vs.setSize(600, 400);
            vs.setVisible(true);
            vs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception ex) {
            System.err.println("Exceção capturada.\n" + ex);
            ex.printStackTrace();
        }
    }
}
