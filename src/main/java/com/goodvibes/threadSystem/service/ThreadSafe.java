package com.goodvibes.threadSystem.service;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class ThreadSafe {
    Connection db;
    Statement st;
    int totalTable = 0;

    public void go(Logger log, String tableName, int amountPerLine) throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {

        InputStream input = new FileInputStream("src/main/resources/config.properties");
        Properties prop = new Properties();

        prop.load(input);

        // TsNames
        System.setProperty(prop.getProperty("db.tsName"), prop.getProperty("db.pathTsName"));

        String url = prop.getProperty("db.url");
        String usr = prop.getProperty("db.user");
        String pwd = prop.getProperty("db.password");

        Class.forName("oracle.jdbc.OracleDriver");

        log.info("Connecting to the database\n URL = " + url);
        db = DriverManager.getConnection(url, usr, pwd);

        log.info("Connected...Creating the declaration");
        st = db.createStatement();

        db.setAutoCommit(true);

        startProcessor(log, tableName, amountPerLine);

        log.info("Closing the connection !");
        log.info("Total successfully deleted items: " + totalTable);
        log.info("------------------------- System Thread: END --------------------------------");
        st.close();
        db.close();
    }

    public void startProcessor(Logger log, String tableName, int amountPerLine) throws SQLException {
        try {
            int itemsDivision3 = verifyQtdItemsInTable(log, tableName) / 3;

            Thread thread1 = new ThreadFirst(log, db, itemsDivision3, amountPerLine, tableName);
            Thread thread2 = new ThreadSecond(log, db, itemsDivision3, amountPerLine, tableName);
            Thread thread3 = new ThreadThird(log, db, itemsDivision3, amountPerLine, tableName);
            Thread thread4 = new ThreadFourth(log, db, itemsDivision3, amountPerLine, tableName);
            Thread thread5 = new ThreadFifth(log, db, itemsDivision3, amountPerLine, tableName);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();

            log.warn("Waiting for the execution of threads");
            while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive() || thread5.isAlive())
                Thread.yield();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private int verifyQtdItemsInTable(Logger log, String tableName) {
        totalTable = 0;
        try {
            ResultSet rs = st.executeQuery("SELECT COUNT(ID_IDIOMA) FROM " + tableName + " WHERE ID_IDIOMA = 'ZH' OR ID_IDIOMA = 'DE'");
            if (rs.next()) {
                totalTable = rs.getInt(1);
                rs.close();
                log.info("Total items in the table: " + totalTable);
            }
        } catch (SQLException sqlEx) {
            log.error(sqlEx.getMessage());
        }
        return totalTable;
    }
}
