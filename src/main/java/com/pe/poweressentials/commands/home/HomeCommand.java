package com.pe.poweressentials.commands.home;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.config.PEConfig;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.manager.user.UserManager;
import com.pe.poweressentials.model.HomeData;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;

public class HomeCommand extends PECommand{

  public HomeCommand(){
    super("home");
    this.setupSectionLang("home");
    this.setPermission("home");
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
      return false;
    }

    Player player = (Player) sender;

    UserManager userManager = UserManager.getUser(player);
    if (userManager == null) {
      player.sendMessage(
          prefix + TextFormat.RED + lang.translateString("errorPlayerNotFound", new String[] { player.getName() }));
      return false;
    }

    // cek list homes
    if (args.length == 0) {
      StringBuilder homes = new StringBuilder();
      for (HomeData home : userManager.getHomeManager().getHomes()) {
        homes.append(home.getName()).append(", ");
      }
      
      sender.sendMessage(prefix + lang.translateString("list","home", new String[] { homes.toString() }));
      return false;
    }

    String name = args[0];

    if (!userManager.getHomeManager().exists(name)) {
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorHomeNotFound", "delhome", new String[] { name }));
      return false;
    }
    HomeData home = userManager.getHomeManager().getHome(name);

    Level world = player.getServer().getLevelByName(home.getWorld());
    if(world == null){
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorWorldNotFound", new String[] { home.getWorld() }));
      return false;
    }

    // world target is blacklisted ?
    if(PEConfig.isHomeBlacklistWorld(world)){
      player.sendMessage(prefix + TextFormat.RED + lang.translateString("errorBlacklistWorld"));
      return false;
    }
    

    player.teleport(new Position(home.getX(), home.getY(), home.getZ(), world));
    player.sendMessage(prefix + lang.translateString("teleported", "home", new String[] { name }));
    return true;
  }
  
}
