package com.goodvibes.threadSystem.service;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadSecond extends Thread {
    Connection c;
    Statement st;
    int vz = 0;
    int qt;
    String tName;
    Logger logger;

    public ThreadSecond(Logger log, Connection c, int qtItems, int qtExclusion, String tableName) throws SQLException {
        this.c = c;
        st = c.createStatement();
        vz = qtItems;
        qt = qtExclusion;
        tName = tableName;
        logger = log;
    }

    public void run() {
        try {
            logger.info("Second thread executing... ");
            for (int i = 0; i < vz; i += qt) {
                st.executeUpdate("DELETE FROM " + tName + " WHERE ID_IDIOMA IN ('ZH','DE') AND ROWNUM <= " + qt);
                logger.warn("Second thread Delete success: " + i);
            }
            logger.info("Second thread end... ");
        } catch (SQLException sqlE) {
            logger.error("Second Thread: " + sqlE.getMessage());
            System.exit(1);
        }
    }
}
