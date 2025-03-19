package com.pe.poweressentials.config;

import java.util.HashMap;
import java.util.Map;

import com.pe.poweressentials.Loader;

import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

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

  public static long getHealCooldown() {
    return config.getLong("heal-cooldown", 60000);
  }

  public static long getFeedCooldown() {
    return config.getLong("feed-cooldown", 60000);
  }

  public static int getHomeDefaultLimit() {
    return config.getInt("home-default-limit", 5);
  }

  public static Map<String, Integer> getHomePermissionLimits() {
    Map<String, Integer> permissions = new HashMap<>();
    ConfigSection sect = config.getSection("home-permission-limits");
    if (sect != null && sect.size() > 0) {
      for (String perm : sect.getKeys(false)) {
        int limit = sect.getInt(perm);
        permissions.put(perm, limit);
      }
    }
    return permissions;
  }

  public static boolean isHomeBlacklistWorld(Level level){
    if(!config.exists("home-blacklist-worlds")){
      return false;
    }
    return config.getStringList("home-blacklist-worlds").contains(level.getName());
  }

  public static boolean isRandomSpawnToHomeAfterDeath(){
    return config.getBoolean("random-spawn-to-home-after-death", true);
  }
}
