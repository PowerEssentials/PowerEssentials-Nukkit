package com.pe.poweressentials.manager.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.pe.poweressentials.Loader;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

public class UserManager {

  private static final Map<String, UserManager> userManagers = new HashMap<>();

  private Player player;
  private Loader loader;
  private Config data;

  private UserHomeManager userHomeManager;

  public UserManager(Loader loader, Player player) {
    this.loader = loader;
    this.player = player;

    File file = new File(loader.getDataFolder() + "/player/" + player.getName().toLowerCase() + ".yml");
    if (!file.exists()) {
      loader.saveResource("player/" + player.getName().toLowerCase() + ".yml");
    }
    this.data = new Config(file, Config.YAML);

    userHomeManager = new UserHomeManager(this);
  }

  public Player getPlayer() {
    return this.player;
  }

  public Loader getLoader() {
    return this.loader;
  }

  public Config getData() {
    return this.data;
  }

  public UserHomeManager getHomeManager() {
    return userHomeManager;
  }

  public static UserManager getUser(Player player) {
    return getUser(player.getName());
  }

  public static UserManager getUser(String playerName) {
    return userManagers.get(playerName.toLowerCase());
  }

  public static void registerUser(Loader loader, Player player) {
    userManagers.put(player.getName().toLowerCase(), new UserManager(loader, player));
  }

  public static void unregisterUser(Player player) {
    if (userManagers.containsKey(player.getName().toLowerCase())) {
      userManagers.remove(player.getName().toLowerCase());
    }
  }
}
