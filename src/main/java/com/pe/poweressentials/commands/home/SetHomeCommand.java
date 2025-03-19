package com.pe.poweressentials.commands.home;

import java.util.Map;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.config.PEConfig;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.user.UserManager;
import com.pe.poweressentials.utils.Utils;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class SetHomeCommand extends PECommand {

  public SetHomeCommand() {
    super("sethome");
    this.setupSectionLang("sethome");
    this.setPermission("sethome");
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

    // cek limit
    if(userManager.getHomeManager().getHomes().length >= myLimit(player) && !player.hasPermission("poweressentials.sethome.bypass.limit")){
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorHomeLimit", "sethome", new String[] { String.valueOf(myLimit(player)) }));
      return false;
    }

    if(PEConfig.isHomeBlacklistWorld(player.getLevel())){
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorBlacklistWorld"));
      return false;
    }

    if (!Utils.textIsClean(name)) {
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorSpecialCharacters"));
      return false;
    }

    if (userManager.getHomeManager().exists(name)) {
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorHomeAlreadyExists", "sethome", new String[] { name }));
      return false;
    }

    userManager.getHomeManager().setHome(name, player.getPosition());
    player.sendMessage(prefix + lang.translateString("created", "sethome", new String[] { name }));
    return true;
  }

  private int myLimit(Player player){
    int currentLimit = PEConfig.getHomeDefaultLimit();
    for(Map.Entry<String, Integer> entry : PEConfig.getHomePermissionLimits().entrySet()){
      if(player.hasPermission(entry.getKey())){
        int limit = entry.getValue();
        if(limit > currentLimit){
          currentLimit = limit;
        }
      }
    }
    return currentLimit;
  }
}
