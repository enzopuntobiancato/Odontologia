package com.utn.tesis.service;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
@Slf4j
public class BackupGenerator {
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static final String DB_HOST = "localhost";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String DB_NAME = "odontologia_db";
    private static final String DB_PORT= "3306";
    private static final String LIN_PATH_MYSQLDUMP = "mysqldump";
    private static final String WIN_PATH_MYSQLDUMP = "mysqldump";

    public File backupDatabase() {
        boolean status = false;
        Process p = null;
        try {
            boolean onLinux = OS.indexOf("linux") >= 0;
            String path;
            if (onLinux) {
                path = ArchivoService.LIN_PATH;
            } else {
                path = ArchivoService.WIN_PATH;
            }
            path = path + File.separator;
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date();
            String filepath = DB_NAME + "-" + "DUMP" + "-(" + dateFormat.format(date) + ").sql";
            String endBatchCommand = " -h " + DB_HOST + " --port " + DB_PORT + " -u " + DB_USER + " --password=" + DB_PASS + " --add-drop-database -B " + DB_NAME + " -r \"" + path + "" + filepath + "\"";
            Runtime rt = Runtime.getRuntime();
            String[] cmd;
            String batchCommand;
            if (onLinux) {
                batchCommand = LIN_PATH_MYSQLDUMP + endBatchCommand;
                cmd = new String[]{"/bin/sh", "-c", batchCommand};
            } else {
                batchCommand = WIN_PATH_MYSQLDUMP + endBatchCommand;
                cmd = new String[]{ "cmd.exe", "/c", batchCommand};
            }

            p = rt.exec(cmd);
            int processComplete = p.waitFor();
            if (processComplete == 0) {
                status = true;
                log.info("Backup created successfully for DB " + DB_NAME + " in " + DB_HOST + ":" + DB_PORT);
            } else {
                status = false;
                log.info("Could not create the backup for DB " + DB_NAME + " in " + DB_HOST + ":" + DB_PORT);
            }
            return new File(path + filepath);

        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return null;
    }
}
