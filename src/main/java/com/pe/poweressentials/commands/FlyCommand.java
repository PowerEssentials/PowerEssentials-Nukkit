package com.pe.poweressentials.commands;

import com.pe.poweressentials.i18n.PELang;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class FlyCommand extends PECommand {

  public FlyCommand() {
    super("fly");
    this.setDescription("fperms");
    this.setPrefix("fly");
    this.setPermission("fly");
    this.setAliases(new String[] { "flight" });
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (args.length == 0) {
      if (sender instanceof Player) {
        Player player = (Player) sender;

        player.sendMessage(
            prefix + (player.getAllowFlight() ? lang.translateString("disabled",
                "fly")
                : lang.translateString("enabled",
                    "fly")));

        player.setAllowFlight(!player.getAllowFlight());
      } else {
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
        return false;
      }
    } else {
      Player targetPlayer = sender.getServer().getPlayer(args[0]);

      if (targetPlayer == null) {
        sender.sendMessage(
            prefix + TextFormat.RED + lang.translateString("errorPlayerNotFound", new String[] { args[0] }));
        return false;
      }

      targetPlayer.sendMessage(prefix
          + (targetPlayer.getAllowFlight() ? lang.translateString("disabled",
              "fly")
              : lang.translateString("enabled",
                  "fly")));

      sender.sendMessage(prefix + (targetPlayer.getAllowFlight()
          ? lang.translateString("otherDisabled",
              "fly", new String[] { targetPlayer.getName() })
          : lang.translateString("otherEnabled",
              "fly", new String[] { targetPlayer.getName() })));

      targetPlayer.setAllowFlight(!targetPlayer.getAllowFlight());

    }
    return true;
  }

}
