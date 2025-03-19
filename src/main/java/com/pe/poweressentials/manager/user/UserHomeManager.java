package com.pe.poweressentials.manager.user;

import java.util.Map;

import com.pe.poweressentials.model.HomeData;
import com.pe.poweressentials.utils.Utils;

import cn.nukkit.level.Position;

public class UserHomeManager {

  private UserManager userManager;

  public UserHomeManager(UserManager userManager) {
    this.userManager = userManager;
  }

  public void setHome(String name, Position pos) {
    if (!Utils.textIsClean(name)) {
      userManager.getLoader().getLogger().error(
          String.format("Error by (%s), home name contains special characters!", userManager.getPlayer().getName()));
      return;
    }

    if (this.exists(name)) {
      userManager.getLoader().getLogger()
          .error(String.format("Error by (%s), home name already exists!", userManager.getPlayer().getName()));
      return;
    }

    Map<String, Object> homeData = Map.of(
        "name", name,
        "world", pos.getLevel().getName(),
        "x", pos.getFloorX(),
        "y", pos.getFloorY(),
        "z", pos.getFloorZ());
    userManager.getData().set("homes." + name, homeData);
    userManager.getData().save();

  }

  public boolean exists(String name) {
    if (!userManager.getData().exists("homes")) {
      return false;
    }
    return userManager.getData().getSection("homes").containsKey(name);
  }

  public void removeHome(String name) {
    if (!this.exists(name)) {
      userManager.getLoader().getLogger().error(
          String.format("Error by (%s), home name does not exist!", userManager.getPlayer().getName()));
      return;
    }
    userManager.getData().getSection("homes").remove(name);
    userManager.getData().save();
  }

  @SuppressWarnings("unchecked")
  public HomeData getHome(String name) {
    if (!this.exists(name)) {
      userManager.getLoader().getLogger().error(
          String.format("Error by (%s), home name does not exist!", userManager.getPlayer().getName()));
      return null;
    }
    Map<String, Object> value = (Map<String, Object>) userManager.getData().getSection("homes").get(name);
    HomeData _HomeData = new HomeData();
    _HomeData.setName((String) value.get("name"));
    _HomeData.setWorld((String) value.get("world"));
    _HomeData.setX((Integer) value.get("x"));
    _HomeData.setY((Integer) value.get("y"));
    _HomeData.setZ((Integer) value.get("z"));
    return _HomeData;
  }

  @SuppressWarnings("unchecked")
  public HomeData[] getHomes() {
    if (!userManager.getData().exists("homes")) {
      return new HomeData[0];
    }
    Map<String, Object> homes = userManager.getData().getSection("homes");
    HomeData[] homeData = new HomeData[homes.size()];
    int i = 0;
    for (Map.Entry<String, Object> entry : homes.entrySet()) {
      Map<String, Object> value = (Map<String, Object>) entry.getValue();
      HomeData _HomeData = new HomeData();
      _HomeData.setName((String) value.get("name"));
      _HomeData.setWorld((String) value.get("world"));
      _HomeData.setX((Integer) value.get("x"));
      _HomeData.setY((Integer) value.get("y"));
      _HomeData.setZ((Integer) value.get("z"));
      homeData[i] = _HomeData;
      i++;
    }
    return homeData;
  }
}
