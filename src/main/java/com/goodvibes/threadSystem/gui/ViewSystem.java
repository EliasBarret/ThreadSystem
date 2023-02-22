package com.goodvibes.threadSystem.gui;

import com.goodvibes.threadSystem.service.ThreadSafe;
import com.goodvibes.threadSystem.util.LogFile;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class ViewSystem extends JFrame {
    public JPanel PanelMain;
    private JTextField inputTableName;
    private JSpinner spinnerAmountPerLine;
    private JButton goButton;
    private JTextArea textAreaLogger;
    private JLabel JLabelTableName;
    private JLabel JLabelQtPerLine;

    Logger log;

    public ViewSystem(Logger logger) {
        log = logger;

        goButton.addActionListener(new ActionListener() {
            LogFile logFile = new LogFile();
            ThreadSafe threadsafe = new ThreadSafe();

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Job JNotify - IMPORTANT unfinished.
                    //logFile.verifyFileLog();

                    log.info("------------------------- System Thread: Start --------------------------------");
                    log.info("Table Name: " + inputTableName.getText() + " " + "Amount Per Line: " + spinnerAmountPerLine.getValue().toString());

                    // Responsible for all delete work
                    threadsafe.go(log, inputTableName.getText(), Integer.parseInt(spinnerAmountPerLine.getValue().toString()));

                    // load logs in screen
                    textAreaLogger.setText(logFile.readLogFile(log));
                } catch (ClassNotFoundException ex) {
                    log.error("ClassNotFoundException: " + ex.getMessage());
                } catch (IOException ex) {
                    log.error("IOException: " + ex.getMessage());
                } catch (SQLException ex) {
                    log.error("SQLException: " + ex.getMessage());
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        });
    }

}
