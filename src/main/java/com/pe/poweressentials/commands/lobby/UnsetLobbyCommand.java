package com.pe.poweressentials.commands.lobby;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.DataManager;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class UnsetLobbyCommand extends PECommand {

  public UnsetLobbyCommand() {
    super("unsetlobby");
    this.setupSectionLang("unsetlobby");
    this.setPermission("unsetlobby");
    this.setAliases(new String[] { "unsethub" });
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
      return false;
    }
    Player player = (Player) sender;
    DataManager.unsetLobby();
    player.sendMessage(prefix + lang.translateString("unsetted", "unsetlobby"));
    return true;
  }
}
