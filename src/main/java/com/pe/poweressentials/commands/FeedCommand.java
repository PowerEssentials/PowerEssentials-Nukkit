package com.pe.poweressentials.commands;

import com.pe.poweressentials.config.PEConfig;
import com.pe.poweressentials.i18n.PELang;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class FeedCommand extends PECommand {

  private static long COOLDOWN_TIME = 60000;
  private final Map<String, Long> cooldowns = new HashMap<>();

  public FeedCommand() {
    super("feed");
    this.setPermission("feed");
    this.setupSectionLang("feed");
    COOLDOWN_TIME = PEConfig.getFeedCooldown();
  }

  @Override
  public boolean run(CommandSender sender, String prefix, PELang lang, String[] args) {
    if (args.length == 0) {
      if (sender instanceof Player) {
        Player player = (Player) sender;
        String playerName = player.getName();

        // Cek cooldown
        long currentTime = System.currentTimeMillis();
        if (cooldowns.containsKey(playerName) && !player.hasPermission("poweressentials.feed.bypass.cooldown")) {
          long lastUsed = cooldowns.get(playerName);
          if (currentTime - lastUsed < COOLDOWN_TIME) {
            long timeLeft = (COOLDOWN_TIME - (currentTime - lastUsed)) / 1000;
            player.sendMessage(prefix + TextFormat.RED
                + lang.translateString("errorCommandCooldown", new String[] { String.valueOf(timeLeft) }));
            return false;
          }
        }

        player.getFoodData().setLevel(player.getFoodData().getMaxLevel());
        cooldowns.put(playerName, currentTime);
        player.sendMessage(prefix + lang.translateString("feeded", "feed"));

      } else {
        sender.sendMessage(prefix + TextFormat.RED + lang.translateString("errorCommandConsole"));
        return false;
      }
    } else {
      if (!sender.hasPermission("poweressentials.feed.others")) {
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
      targetName = targetPlayer.getName();

      targetPlayer.getFoodData().setLevel(targetPlayer.getFoodData().getMaxLevel());
      targetPlayer.sendMessage(prefix + lang.translateString("feeded", "feed"));
      sender.sendMessage(prefix + lang.translateString("othersFeeded", "feed", new String[] { targetPlayer.getName() }));
    }
    return true;
  }

}
