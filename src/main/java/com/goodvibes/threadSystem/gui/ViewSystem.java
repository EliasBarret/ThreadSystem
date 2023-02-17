package com.goodvibes.threadSystem.gui;

import com.goodvibes.threadSystem.service.ThreadSafe;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class ViewSystem extends JFrame {
    public JPanel PanelMain;
    private JLabel JLabelTableName;
    private JTextField inputTableName;
    private JSpinner spinnerAmountPerLine;
    private JButton goButton;
    private JButton exitButton;
    private JTextArea textAreaLogger;
    private JLabel JLabelQtPerLine;
    Logger log;

    public ViewSystem(Logger logger) {
        log = logger;

        goButton.addActionListener(new ActionListener() {
            ThreadSafe threadsafe = new ThreadSafe();

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    log.info("Getting inputs!!");
                    threadsafe.go(log, inputTableName.getText(), Integer.parseInt(spinnerAmountPerLine.getValue().toString()));
                    log.info("Table Name: " + inputTableName.getText() + "\n" + "Amount Per Line: " + spinnerAmountPerLine.getValue().toString());
                } catch (ClassNotFoundException ex) {
                    log.error("ClassNotFoundException: " + ex.getMessage());
                } catch (IOException ex) {
                    log.error("IOException: " + ex.getMessage());
                } catch (SQLException ex) {
                    log.error("SQLException: " + ex.getMessage());
                }
            }
        });
    }
}
