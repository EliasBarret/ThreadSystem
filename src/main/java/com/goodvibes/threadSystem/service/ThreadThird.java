package com.goodvibes.threadSystem.service;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadThird extends Thread{
    Connection c;
    Statement st;
    int vz = 0;
    int qt;
    String tName;
    Logger logger;

    public ThreadThird(Logger log, Connection c, int qtItems, int qtExclusion, String tableName) throws SQLException {
        this.c = c;
        st = c.createStatement();
        vz = qtItems;
        qt = qtExclusion;
        tName = tableName;
        logger = log;
    }

    public void run() {
        try {
            logger.info("Third thread executing ...");
            for (int i = 0; i < vz; i += qt) {
                st.executeUpdate("DELETE FROM " + tName + " WHERE ID_IDIOMA IN ('ZH','DE') AND ROWNUM <= " + qt);
                logger.warn("Third thread Delete success: "+ qt + " rows deleted!");
            }
            logger.info("Third thread end ... ");
        } catch (SQLException sqlE) {
            logger.error("Third thread: " + sqlE.getMessage());
            System.exit(1);
        }
    }
}
