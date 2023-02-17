package com.goodvibes.threadSystem.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogGenerator {

    public static void generateLog(String message) throws IOException {

        Path path = Paths.get("C:/Users/55479/Desktop/logs/");

        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }

        File log = new File(System.getProperty("java.io.tmpdir")+ File.separator + "newFile.txt");

        if(!log.exists()) {
            log.createNewFile();
        }

        FileWriter fw = new FileWriter(log, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(message);
        bw.newLine();

        bw.close();
        fw.close();
    }
}
