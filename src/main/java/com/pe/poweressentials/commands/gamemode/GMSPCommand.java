package com.pe.poweressentials.commands.gamemode;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.i18n.PELang;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class GMSPCommand extends PECommand {
  
  public GMSPCommand() {
    super("gmsp");
    this.setPermission("gmsp");
    this.setupSectionLang("gamemode");
    this.setAliases(new String[] { "gmspc" });
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (args.length == 0) {
      if (sender instanceof Player) {
        Player player = (Player) sender;
        player.setGamemode(Player.SPECTATOR);
        player.sendMessage(prefix + lang.translateString("changed", "gamemode", new String[] { "spectator" }));
      } else {
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
        return false;
      }

    } else {
      if (!sender.hasPermission("poweressentials.gmsp.others")) {
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorNoPermission"));
        return false;
      }

      String targetName = args[0];
      Player targetPlayer = sender.getServer().getPlayer(targetName);
      if (targetPlayer == null) {
        sender.sendMessage(
            prefix + TextFormat.RED + lang.translateString("errorPlayerNotFound", new String[] { targetName }));
        return false;
      }

      targetPlayer.setGamemode(Player.SPECTATOR);
      sender.sendMessage(prefix
          + lang.translateString("othersChanged", "gamemode", new String[] { targetPlayer.getName(), "spectator" }));
    }
    return true;
  }
}
