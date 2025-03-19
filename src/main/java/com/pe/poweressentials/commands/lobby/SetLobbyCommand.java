package com.pe.poweressentials.commands.lobby;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.DataManager;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class SetLobbyCommand extends PECommand {

  public SetLobbyCommand() {
    super("setlobby");
    this.setupSectionLang("setlobby");
    this.setPermission("setlobby");
    this.setAliases(new String[] { "sethub" });
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
      return false;
    }
    Player player = (Player) sender;
    DataManager.setLobby(player.getPosition());
    player.sendMessage(prefix + lang.translateString("setted", "setlobby"));
    return true;
  }

}
