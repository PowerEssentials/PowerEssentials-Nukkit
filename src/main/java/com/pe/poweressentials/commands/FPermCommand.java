package com.pe.poweressentials.commands;

import com.pe.poweressentials.i18n.PELang;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.TextFormat;

public class FPermCommand extends PECommand {

  public FPermCommand() {
    super("fperm");
    this.setDescription("fperm");
    this.setPrefix("fperm");
    this.setPermission("fperm");
    this.setUsage("fperm");
    this.setAliases(new String[] { "fperms" });
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(prefix + TextFormat.YELLOW + this.getUsage());
      return false;
    }

    String pluginName = args[0];
    Plugin plugin = Server.getInstance().getPluginManager().getPlugin(pluginName);
    if (plugin == null) {
      sender.sendMessage(
          prefix + TextFormat.RED + lang.translateString("errorPluginNotFound", new String[] { pluginName }));
      return false;
    }
    StringBuilder listPerm = new StringBuilder();
    int i = 1;
    for (Permission perm : plugin.getDescription().getPermissions()) {
      listPerm.append("\n").append(TextFormat.GOLD).append(" ").append(i++).append(". ").append(perm.getName()).append(": ")
          .append(perm.getDefault());
    }
    sender.sendMessage(prefix + lang.translateString("result", "fperm",
        new String[] { plugin.getName(), plugin.getDescription().getPermissions().size() + "", listPerm.toString() }));
    return true;
  }

}
