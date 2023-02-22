package com.goodvibes.threadSystem.service;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadFourth extends Thread{
    Connection c;
    Statement st;
    int vz = 0;
    int qt;
    String tName;
    Logger logger;

    public ThreadFourth(Logger log, Connection c, int qtItems, int qtExclusion, String tableName) throws SQLException {
        this.c = c;
        st = c.createStatement();
        vz = qtItems;
        qt = qtExclusion;
        tName = tableName;
        logger = log;
    }

    @Override
    public void run() {
        try {
            logger.info("Fourth thread executing...");
            int i;
            for (i = 0; i < vz; i += qt) {
                st.executeUpdate("DELETE FROM " + tName + " WHERE ID_IDIOMA IN ('ZH','DE') AND ROWNUM <= " + qt);
            }
            logger.info("Fourth thread got: "+ i + " rows deleted!");
        } catch (SQLException sqlE) {
            logger.error("Fourth thread: " + sqlE);
            System.exit(1);
        }
    }
}
