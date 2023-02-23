package com.goodvibes.threadSystem.gui;

import com.goodvibes.threadSystem.service.ThreadSafe;
import com.goodvibes.threadSystem.util.LogFile;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ViewSystem extends JFrame {
    public JPanel PanelMain;
    private JPanel tablePanelLog;
    private JPanel tablePanelBDConfig;
    private JLabel JLabelTableName;
    private JLabel JLabelQtPerLine;
    private JTabbedPane tabbedPane1;
    private JTextField inputTableName;
    private JButton goButton;
    private JTextArea textAreaLogger;
    private JSpinner spinnerAmountPerLine;
    private JTextField textFieldUrl;
    private JTextField textFieldUser;
    private JPasswordField passwordField;
    private JCheckBox checkBoxUseTsNames;
    private JButton btSelectDirectory;
    private JTextField textFieldPathTsName;
    private JLabel labelSelectDirTsName;
    private JProgressBar pBarLoad;

    Logger log;

    public ViewSystem(Logger logger) {
        log = logger;

        SpinnerModel value = new SpinnerNumberModel(1, 0, 10000, 1);
        spinnerAmountPerLine = new JSpinner(value);

        textFieldPathTsName.setVisible(false);
        btSelectDirectory.setVisible(false);
        labelSelectDirTsName.setVisible(false);


        goButton.addActionListener(new ActionListener() {
            LogFile logFile = new LogFile();
            ThreadSafe threadsafe = new ThreadSafe();

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Job JNotify - IMPORTANT unfinished.
                    // logFile.verifyFileLog();

                    // validateField();

                    log.info("------------------------- System Thread: Start --------------------------------");
                    log.info("Table Name: " + inputTableName.getText() + " " + "Amount Per Line: " + spinnerAmountPerLine.getValue().toString());

                    // Responsible for all delete work
                    threadsafe.go(log, inputTableName.getText(), Integer.parseInt(spinnerAmountPerLine.getValue().toString()), textFieldUrl.getText(), textFieldUser.getText(), String.copyValueOf(passwordField.getPassword()), checkBoxUseTsNames.isSelected(), textFieldPathTsName.getText(), pBarLoad);

                } catch (ClassNotFoundException ex) {
                    log.error("ClassNotFoundException: " + ex.getMessage());
                } catch (IOException ex) {
                    log.error("IOException: " + ex.getMessage());
                } catch (SQLException ex) {
                    log.error("SQLException: " + ex.getMessage());
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                textAreaLogger.setText(logFile.readLogFile(log));
            }
        });

        btSelectDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int i = file.showSaveDialog(null);
                if (i == 1) {
                    textFieldPathTsName.setText("");
                } else {
                    File arq = file.getSelectedFile();
                    textFieldPathTsName.setText(arq.getPath());
                }
            }
        });
        checkBoxUseTsNames.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (checkBoxUseTsNames.isSelected()) {
                    textFieldPathTsName.setVisible(true);
                    btSelectDirectory.setVisible(true);
                    labelSelectDirTsName.setVisible(true);
                } else {
                    textFieldPathTsName.setVisible(false);
                    btSelectDirectory.setVisible(false);
                    labelSelectDirTsName.setVisible(false);
                }
            }
        });
    }

    private void validateField() {
        if (inputTableName.getText().length() > 0) {
            inputTableName.setVisible(false);
        } else {
            inputTableName.setVisible(true);
        }
        if ((Integer) spinnerAmountPerLine.getValue() > 0) {
            spinnerAmountPerLine.setVisible(false);
        } else {
            spinnerAmountPerLine.setVisible(true);
        }
        if (textFieldUrl.getText().length() > 0) {
            textFieldUrl.setVisible(false);
        } else {
            textFieldUrl.setVisible(true);
        }
        if (textFieldUser.getText().length() > 0) {
            textFieldUser.setVisible(false);
        } else {
            textFieldUser.setVisible(true);
        }
        if (passwordField.getPassword().length > 0) {
            passwordField.setVisible(false);
        } else {
            passwordField.setVisible(true);
        }
        if (checkBoxUseTsNames.isSelected()) {
            if (textFieldPathTsName.getText().length() > 0) {
            } else {
                JOptionPane.showMessageDialog(null, "Check required tsNames path field !!");
            }
        }

        if (inputTableName.getText().length() > 0 && (Integer) spinnerAmountPerLine.getValue() > 0 && textFieldUrl.getText().length() > 0 && textFieldUser.getText().length() > 0 && passwordField.getPassword().length > 0) {
        } else {
            JOptionPane.showMessageDialog(null, "Check required fields !!");
        }
    }
}

