package com.pe.poweressentials.commands.home;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.user.UserManager;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class DelHomeCommand extends PECommand {

  public DelHomeCommand() {
    super("delhome");
    this.setupSectionLang("delhome");
    this.setPermission("delhome");
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
      return false;
    }

    if (args.length == 0) {
      sender.sendMessage(prefix + TextFormat.RED + this.getUsage());
      return false;
    }

    Player player = (Player) sender;
    String name = args[0];

    UserManager userManager = UserManager.getUser(player);
    if (userManager == null) {
      player.sendMessage(
          prefix + TextFormat.RED + lang.translateString("errorPlayerNotFound", new String[] { player.getName() }));
      return false;
    }

    if (!userManager.getHomeManager().exists(name)) {
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorHomeNotFound", "delhome", new String[] { name }));
      return false;
    }

    userManager.getHomeManager().removeHome(name);
    player.sendMessage(prefix + lang.translateString("deleted", "delhome", new String[] { name }));

    return true;
  }
}
