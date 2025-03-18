package com.pe.poweressentials.commands;

import com.pe.poweressentials.i18n.PELang;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public abstract class PECommand extends Command {

  public static String PREFIX_PERMISSION = "poweressentials.";
  private String prefix;

  public PECommand(String name) {
    super(name);
  }

  @Override
  public void setPermission(String permission) {
    super.setPermission(PREFIX_PERMISSION + permission);
  }

  @Override
  public void setDescription(String sectionLang) {
    super.setDescription(PELang.fromConsole().translateString("description", sectionLang));
  }

  @Override
  public void setUsage(String sectionLang) {
    super.setUsage(PELang.fromConsole().translateString("usage", sectionLang));
  }

  public void setupSectionLang(String sectionLang){
    this.setDescription(sectionLang);
    this.setPrefix(sectionLang);
    this.setUsage(sectionLang);
  }

  public void setPrefix(String sectionLang) {
    this.prefix = TextFormat.GOLD + PELang.fromConsole().translateString("prefix", sectionLang) + " ";
  }

  public String getPrefix() {
    return this.prefix;
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    if (!this.testPermission(sender))
      return false;
    return this.run(sender, this.prefix, PELang.fromConsole(), args);
  }

  abstract public boolean run(CommandSender sender, String prefix, PELang lang, String[] args);

}
