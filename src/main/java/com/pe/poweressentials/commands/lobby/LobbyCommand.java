package com.pe.poweressentials.commands.lobby;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.DataManager;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class LobbyCommand extends PECommand {

  public LobbyCommand() {
    super("lobby");
    this.setupSectionLang("lobby");
    this.setPermission("lobby");
    this.setAliases(new String[] { "hub" });
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
      return false;
    }
    Player player = (Player) sender;
    player.teleport(DataManager.getLobby());
    player.sendMessage(prefix + lang.translateString("teleported", "lobby"));
    return true;
  }

}
