package com.pe.poweressentials;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.pe.poweressentials.commands.FPermCommand;
import com.pe.poweressentials.commands.FeedCommand;
import com.pe.poweressentials.commands.FlyCommand;
import com.pe.poweressentials.commands.HealCommand;
import com.pe.poweressentials.commands.PECommand;
import com.pe.poweressentials.commands.gamemode.GMACommand;
import com.pe.poweressentials.commands.gamemode.GMCCommand;
import com.pe.poweressentials.commands.gamemode.GMSCommand;
import com.pe.poweressentials.commands.gamemode.GMSPCommand;
import com.pe.poweressentials.commands.home.DelHomeCommand;
import com.pe.poweressentials.commands.home.HomeCommand;
import com.pe.poweressentials.commands.home.SetHomeCommand;
import com.pe.poweressentials.commands.lobby.LobbyCommand;
import com.pe.poweressentials.commands.lobby.SetLobbyCommand;
import com.pe.poweressentials.commands.lobby.UnsetLobbyCommand;
import com.pe.poweressentials.config.PEConfig;
import com.pe.poweressentials.i18n.PELang;
import com.pe.poweressentials.listener.EventListener;
import com.pe.poweressentials.manager.DataManager;
import com.pe.poweressentials.utils.Utils;

import cn.nukkit.plugin.PluginBase;

public class Loader extends PluginBase {

    private static Loader instance;

    @Override
    public void onEnable() {
        super.onEnable();
        loadResources();
        loadCommands();

        DataManager.init(this);

        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        instance = this;
    }

    private void loadResources() {
        /** place this on first */
        PEConfig.init();

        // remove old language folder
        File oldLanguageDir = new File(this.getDataFolder() + "/language");
        if (oldLanguageDir.exists() && oldLanguageDir.isDirectory()) {
            Utils.unlinkRecursive(oldLanguageDir);
        }

        try {
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources("language");

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                String fileName = resource.getPath().substring(resource.getPath().lastIndexOf('/') + 1);

                if (!fileName.endsWith("." + PELang.LANGUAGE_EXTENSION)) {
                    continue;
                }

                this.saveResource("language/" + fileName, true);
                this.getLogger().info("Loaded language file: " + fileName);
            }

            // Inisialisasi instance PELang
            new PELang(this);

            PELang.setConsoleLocale(PEConfig.getLang());
            this.getLogger().info("Selected language: " + PEConfig.getLang() + ".ini");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCommands() {
        Map<String, List<PECommand>> commands = Map.of(
                "fly", List.of(new FlyCommand()),
                "fperm", List.of(new FPermCommand()),
                "heal", List.of(new HealCommand()),
                "feed", List.of(new FeedCommand()),
                "gamemode", List.of(new GMCCommand(), new GMSCommand(), new GMSPCommand(), new GMACommand()),
                "home", List.of(new HomeCommand(), new SetHomeCommand(), new DelHomeCommand()),
                "lobby", List.of(new LobbyCommand(), new SetLobbyCommand(), new UnsetLobbyCommand()));

        for (Map.Entry<String, List<PECommand>> entry : commands.entrySet()) {
            if (!PEConfig.isCommandDisabled(entry.getKey())) {
                this.getServer().getCommandMap().registerAll(this.getName(), entry.getValue());
            }
        }

    }

    public static Loader getInstance() {
        return instance;
    }

}