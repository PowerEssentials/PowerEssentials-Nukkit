package com.pe.poweressentials.config;

import com.pe.poweressentials.Loader;

import cn.nukkit.utils.Config;

public class PEConfig {

  private static Config config;
  private static String CONFIG_NEW_VERSION = "1.0";

  public static void init() {
    Loader.getInstance().saveDefaultConfig();
    config = Loader.getInstance().getConfig();
  }

  public static String getNewVersion() {
    return CONFIG_NEW_VERSION;
  }

  public static String getCurrentVersion() {
    return config.getString("config-version", "1.0");
  }

  public static String getLang() {
    return config.getString("lang", "en_us");
  }

  public static boolean isCommandDisabled(String command) {
    return config.getStringList("disabled-commands").contains(command.toLowerCase());
  }
}
