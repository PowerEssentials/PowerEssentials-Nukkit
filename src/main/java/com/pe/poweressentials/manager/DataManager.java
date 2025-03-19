package com.pe.poweressentials.manager;

import java.util.Map;

import com.pe.poweressentials.Loader;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;

public class DataManager {

  private static Config data;
  private static Loader loader;

  public static void init(Loader loader) {
    DataManager.loader = loader;
    loader.saveResource("data.yml");
    data = new Config(loader.getDataFolder() + "/data.yml", Config.YAML);
  }

  public static Config getData() {
    return data;
  }

  public static Loader getLoader() {
    return loader;
  }

  public static void setLobby(Position pos) {
    data.set("lobby", Map.of(
        "world", pos.getLevel().getName(),
        "x", pos.getFloorX(),
        "y", pos.getFloorY(),
        "z", pos.getFloorZ()));
    data.save();
  }

  public static void unsetLobby(){
    data.remove("lobby");
    data.save();
  }

  public static Position getLobby(){
    Map<String, Object> lobby = data.getSection("lobby");

    if(lobby == null){
      return Server.getInstance().getDefaultLevel().getSafeSpawn();
    }

    Level world = loader.getServer().getLevelByName((String) lobby.get("world"));
    if(world == null){
      return Server.getInstance().getDefaultLevel().getSafeSpawn();
    }

    return new Position(
      (int) lobby.get("x"),
      (int) lobby.get("y"),
      (int) lobby.get("z"),
      world
    );
  }
}
