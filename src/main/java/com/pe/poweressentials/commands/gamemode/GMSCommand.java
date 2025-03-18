package com.pe.poweressentials.commands.gamemode;

import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.i18n.PELang;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class GMSCommand extends PECommand {

  public GMSCommand() {
    super("gms");
    this.setPermission("gms");
    this.setupSectionLang("gamemode");
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if(args.length == 0){
      if(sender instanceof Player){
        Player player = (Player) sender;
        player.setGamemode(Player.SURVIVAL);
        player.sendMessage(prefix + lang.translateString("changed", "gamemode", new String[]{"survival"}));
      }else{
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
        return false;
      }
    }else{
      if(!sender.hasPermission("poweressentials.gms.others")){
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorNoPermission"));
        return false;
      }
      String targetName = args[0];
      Player targetPlayer = sender.getServer().getPlayer(targetName);
      if(targetPlayer == null){
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorPlayerNotFound", new String[]{targetName}));
        return false;
      }

      targetPlayer.setGamemode(Player.SURVIVAL);
      sender.sendMessage(prefix + lang.translateString("othersChanged", "gamemode", new String[]{targetPlayer.getName(), "survival"}));
    }
    return true;
  }
}
