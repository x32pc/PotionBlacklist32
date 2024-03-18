package com.x32pc.github;

import com.x32pc.github.commands.BaseCommand;
import com.x32pc.github.commands.PotionBlacklistCommand;
import com.x32pc.github.commands.PotionUnblacklistCommand;
import com.x32pc.github.event.CheckDrinkEvent;
import com.x32pc.github.event.CheckLingeringEvent;
import com.x32pc.github.event.CheckSplashEvent;
import com.x32pc.github.manager.DatabaseManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PotionBlacklist32 extends JavaPlugin {

    public PotionBlacklistCommand potionBlacklistCommand;
    public CheckDrinkEvent checkDrinkEvent;
    public DatabaseManager databaseManager;
    public PotionUnblacklistCommand potionUnblacklistCommand;
    public CheckLingeringEvent checkLingeringEvent;
    public CheckSplashEvent checkSplashEvent;

    @Override
    public void onEnable() {
        registerFiles();
        registerCommands();
        registerEvents();
        databaseManager = new DatabaseManager(getDataFolder() + File.separator + "database.db", this);
        saveDefaultConfig();

        if (databaseManager.getConnection() == null) {
            getLogger().severe("Failed to initialize database.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        this.databaseManager.closeConnection();
    }

    public void registerFiles() {
        potionBlacklistCommand = new PotionBlacklistCommand(this);
        checkDrinkEvent = new CheckDrinkEvent(this);
        potionUnblacklistCommand = new PotionUnblacklistCommand(this);
        checkLingeringEvent = new CheckLingeringEvent(this);
        checkSplashEvent = new CheckSplashEvent(this);
    }

    public void registerCommands() {
        getCommand("potion").setExecutor(new BaseCommand(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new CheckDrinkEvent(this), this);
        getServer().getPluginManager().registerEvents(new CheckLingeringEvent(this), this);
        getServer().getPluginManager().registerEvents(new CheckSplashEvent(this), this);
    }
}
