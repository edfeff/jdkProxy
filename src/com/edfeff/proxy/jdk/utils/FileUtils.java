package com.edfeff.proxy.jdk.utils;

import java.io.*;

/**
 * @author wangpp
 */
public class FileUtils {

    public static void saveStringToFile(String str, File file) {
        try (
                FileWriter out = new FileWriter(file)
        ) {

            out.write(str);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File file) {
        file.deleteOnExit();
    }
}
