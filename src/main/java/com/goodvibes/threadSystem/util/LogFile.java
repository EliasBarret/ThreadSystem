package com.goodvibes.threadSystem.util;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;
import org.apache.log4j.Logger;

import java.io.*;

public class LogFile {
    public String readLogFile(Logger log) {
        String nome = new File("log.txt").getAbsolutePath();
        String str, text = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(nome));
            while ((str = in.readLine()) != null) {
                text += str + "\n";
            }
            System.out.println(text);
            in.close();
        } catch (IOException ioe) {
            log.error(ioe.getMessage());
        }
        return text;
    }

    public static void verifyFileLog() throws Exception {

        String path = new File("log.txt").getAbsolutePath();

        int mask = JNotify.FILE_CREATED |
                JNotify.FILE_DELETED |
                JNotify.FILE_MODIFIED |
                JNotify.FILE_RENAMED;

        boolean watchSubtree = true;

        // adiciona o "MONITORADOR"
        int watchID = JNotify.addWatch(path, mask, watchSubtree, new Listener());

        // Code disabled
        // Thread.sleep(1000000);

        // aqui remove o seu "MONITORADOR"
        boolean res = JNotify.removeWatch(watchID);

        if (!res) {
        }
    }

    static class Listener implements JNotifyListener {
        public void fileModified(int wd, String rootPath, String name) {
            print("modificado " + rootPath + " : " + name);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File("log.txt").getAbsolutePath()));

                String dados[] = new String[3];
                String linha = reader.readLine();
                while (linha != null) {
                    linha = reader.readLine();
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }

        public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
            print("renomeado " + rootPath + " : " + oldName + " -> " + newName);
        }

        public void fileDeleted(int wd, String rootPath, String name) {

            print("deletado " + rootPath + " : " + name);
        }

        public void fileCreated(int wd, String rootPath, String name) {

            print("criado " + rootPath + " : " + name);
        }

        void print(String msg) {
            System.err.println(msg);
        }
    }
}
