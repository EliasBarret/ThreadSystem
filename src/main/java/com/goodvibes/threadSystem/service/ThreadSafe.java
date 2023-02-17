package com.goodvibes.threadSystem.service;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class ThreadSafe {
    Connection db;
    Statement st;

    public void go(Logger log, String tableName, int amountPerLine) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
        System.setProperty("oracle.net.tns_admin", "C:/Users/e-djunior/Documents/tnsnames");

        String url = "jdbc:oracle:thin:@DOC_DESEN.WEG.NET";
        String usr = "inout";
        String pwd = "1n0ut_2o21";

        Class.forName("oracle.jdbc.OracleDriver");

        log.info("Connecting to the database\n URL = " + url);
        db = DriverManager.getConnection(url, usr, pwd);

        log.info("Connected...Creating the declaration");
        st = db.createStatement();

        db.setAutoCommit(true);

        startProcessor(log, tableName, amountPerLine);

        log.info("Closing the connection !");
        st.close();
        db.close();
    }

    public void startProcessor(Logger log, String tableName, int amountPerLine) throws SQLException {
        try {
            int itemsDivision3 = verifyQtdItemsInTable(log, tableName) / 3;

            Thread thread1 = new ThreadFirst(log, db, itemsDivision3, amountPerLine, tableName);
            Thread thread2 = new ThreadSecond(log, db, itemsDivision3, amountPerLine, tableName);
            Thread thread3 = new ThreadThird(log, db, itemsDivision3, amountPerLine, tableName);

            thread1.start();
            thread2.start();
            thread3.start();

            log.warn("Waiting for the execution of threads");
            while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive()) Thread.yield();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private int verifyQtdItemsInTable(Logger log, String tableName) {
        int qtd = 0;
        try {
            log.info("Thread one executing...");

            ResultSet rs = st.executeQuery("SELECT COUNT(ID_IDIOMA) FROM " + tableName + " WHERE ID_IDIOMA = 'ZH' OR ID_IDIOMA = 'DE'");
            if (rs.next()) {
                qtd = rs.getInt(1);
                rs.close();
                log.info("Total items in the table: " + qtd);
            }
        } catch (SQLException sqlEx) {
            log.error(sqlEx.getMessage());
        }
        return qtd;
    }
}
