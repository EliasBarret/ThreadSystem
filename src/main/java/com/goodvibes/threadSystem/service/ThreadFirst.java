package com.goodvibes.threadSystem.service;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadFirst extends Thread {
    Connection c;
    Statement st;
    int vz = 0;
    int qt;
    String tName;
    Logger logger;
    JProgressBar progressBar;


    public ThreadFirst(Logger log, Connection c, int qtItems, int qtExclusion, String tableName, JProgressBar pBarLoad) throws SQLException {
        this.c = c;
        st = c.createStatement();
        vz = qtItems;
        qt = qtExclusion;
        tName = tableName;
        logger = log;
        progressBar = pBarLoad;
    }

    public void run() {
        try {
            logger.info("First thread executing...");
            int i;
            for (i = 0; i < vz; i += qt) {
                st.executeUpdate("DELETE FROM " + tName + " WHERE ID_IDIOMA IN ('ZH','DE') AND ROWNUM <= " + qt);
                progressBar.setValue(i);
            }
            logger.info("First thread got: "+ i + " rows deleted!");
        } catch (SQLException sqlE) {
            logger.error("First thread: " + sqlE);
            System.exit(1);
        }
    }
}
