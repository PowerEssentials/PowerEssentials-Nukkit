package com.pe.poweressentials.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.pe.poweressentials.Loader;
import com.pe.poweressentials.config.PEConfig;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.DataManager;
import com.pe.poweressentials.manager.user.UserManager;
import com.pe.poweressentials.model.HomeData;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;

public class EventListener implements Listener {

  private Loader loader;

  private List<String> firstJoins = new ArrayList<>();
  private List<String> playerDeaths = new ArrayList<>();

  public EventListener(Loader loader) {
    this.loader = loader;
  }

  @EventHandler
  public void onLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    UserManager.registerUser(this.loader, player);

  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!firstJoins.contains(player.getName())) {
      player.teleport(DataManager.getLobby());
      firstJoins.add(player.getName());
    }
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    UserManager.unregisterUser(player);

  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    playerDeaths.add(player.getName());
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    Player player = event.getPlayer();
    String playerName = player.getName();

    // spawn on random home, if player death
    if (playerDeaths.contains(playerName) && PEConfig.isRandomSpawnToHomeAfterDeath()) {
      UserManager userManager = UserManager.getUser(player);
      if (userManager == null) {
        return;
      }
      HomeData[] homes = userManager.getHomeManager().getHomes();
      if (homes.length == 0) {
        return;
      }

      int randomIndex = ThreadLocalRandom.current().nextInt(homes.length);
      HomeData randomHome = homes[randomIndex];

      Level worldHome = this.loader.getServer().getLevelByName(randomHome.getWorld());
      if (worldHome == null) {
        return;
      }

      playerDeaths.remove(playerName);
      event.setRespawnPosition(new Position(randomHome.getX(), randomHome.getY(), randomHome.getZ(), worldHome));
      player.sendMessage(TextFormat.GOLD + PELang.fromConsole().translateString("prefix", "home")
          + " " + PELang.fromConsole().translateString("teleported", "home", new String[] { randomHome.getName() }));
    }

  }

}
