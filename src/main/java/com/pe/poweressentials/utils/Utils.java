package com.pe.poweressentials.utils;

import java.io.File;

public class Utils {

  public static boolean unlinkRecursive(File directory) {

    if (!directory.exists() || !directory.isDirectory()) {
      return false;
    }

    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          unlinkRecursive(file);
        } else {
          if (!file.delete()) {
            System.err.println("Failed to delete file: " + file);
          }
        }
      }
    }
    return directory.delete();
  }

  public static boolean unlinkRecursive(String dir) {
    File directory = new File(dir);
    return unlinkRecursive(directory);
  }
}
