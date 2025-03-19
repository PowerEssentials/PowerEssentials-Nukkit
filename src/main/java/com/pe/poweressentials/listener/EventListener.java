package com.pe.poweressentials.listener;

import com.pe.poweressentials.Loader;
import com.pe.poweressentials.manager.user.UserManager;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

  private Loader loader;

  public EventListener(Loader loader) {
    this.loader = loader;
  }

  @EventHandler
  public void onLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    UserManager.registerUser(this.loader, player);
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event){
    Player player = event.getPlayer();
    UserManager.unregisterUser(player);
  }

}
