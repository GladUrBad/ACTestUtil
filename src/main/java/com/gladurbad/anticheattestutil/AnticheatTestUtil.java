package com.gladurbad.anticheattestutil;

import com.gladurbad.anticheattestutil.commands.CommandManager;
import com.gladurbad.anticheattestutil.handlers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnticheatTestUtil extends JavaPlugin {

    public static AnticheatTestUtil instance;
    private static final CommandManager commandManager = new CommandManager();
    private static final WarpHandler warpHandler = new WarpHandler();
    private static final ClearItemHandler clearItemHandler = new ClearItemHandler();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginManager().registerEvents(new ScaffoldAreaHandler(), this);
        Bukkit.getPluginManager().registerEvents(new ChatHandler(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportSignHandler(), this);
        Bukkit.getPluginManager().registerEvents(new ViolationScoreboardHandler(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnHandler(), this);
        Bukkit.getPluginManager().registerEvents(new MedusaAlertHandler(), this);
        Bukkit.getPluginManager().registerEvents(new SpeedTestHandler(), this);
        this.getCommand("pvp").setExecutor(commandManager);
        this.getCommand("help").setExecutor(commandManager);
        this.getCommand("invclear").setExecutor(commandManager);
        this.getCommand("warp").setExecutor(warpHandler);
        this.getCommand("spawn").setExecutor(commandManager);
        this.getCommand("colour").setExecutor(commandManager);
        this.getCommand("alerts").setExecutor(commandManager);


        clearItemHandler.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        clearItemHandler.stop();
    }
}
