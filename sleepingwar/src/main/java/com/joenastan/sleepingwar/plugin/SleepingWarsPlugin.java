package com.joenastan.sleepingwar.plugin;

import com.joenastan.sleepingwar.plugin.commands.HostBedwarsCommand;
import com.joenastan.sleepingwar.plugin.commands.SleepingWarsPermissionsCommand;
import com.joenastan.sleepingwar.plugin.commands.TabCompletor.HostingCommands;
import com.joenastan.sleepingwar.plugin.commands.TabCompletor.SworldCommands;
import com.joenastan.sleepingwar.plugin.commands.WorldMakerCommand;
import com.joenastan.sleepingwar.plugin.events.OnBuilderModeEvents;
import com.joenastan.sleepingwar.plugin.events.OnGameEvent;
import com.joenastan.sleepingwar.plugin.game.GameManager;
import com.joenastan.sleepingwar.plugin.utility.GameSystemConfig;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SleepingWarsPlugin extends JavaPlugin {

    private static JavaPlugin plugin;
    private static GameSystemConfig systemConfig;
    private static GameManager gameManager;

    // Private Variable Getter
    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static GameSystemConfig getGameSystemConfig() {
        return systemConfig;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void onEnable() {
        // Initial Loader
        plugin = this;
        initSW();

        // Load Game Events
        getServer().getPluginManager().registerEvents(new OnGameEvent(), this);
        getServer().getPluginManager().registerEvents(new OnBuilderModeEvents(), this);

        // Load Commands
        getCommand("SWorld").setExecutor(new WorldMakerCommand());
        getCommand("SWorld").setTabCompleter(new SworldCommands());
        getCommand("Bedwars").setExecutor(new HostBedwarsCommand());
        getCommand("Bedwars").setTabCompleter(new HostingCommands());
        getCommand("SwPerm").setExecutor(new SleepingWarsPermissionsCommand());

        System.out.println("~ Ready to Sleep! ~");
    }

    @Override
    public void onDisable() {
        // Free up memories
        plugin = null;
        systemConfig.Save();
        systemConfig = null;
        gameManager.cleanManager();
        gameManager = null;
        OnBuilderModeEvents.clearStatic();
        HandlerList.unregisterAll(this);

        System.out.println("Sleeping war is over, see you next time!");
    }

    // Other Stuff in plugin
    // Initialize plugin before activation
    private void initSW() {
        // Create Directory if not exists
        if (!(getDataFolder().exists())) {
            boolean created = getDataFolder().mkdir();
            if (!created) {
                System.out.println("Could not create SleepingWars folder!");
            }
        }

        // Create inner directories inside Data Folder if not exists
        File folderPath = new File("plugins/SleepingWars/WorldList");
        if (!(folderPath.exists())) {
            boolean created = folderPath.mkdir();
            if (!created) {
                System.out.println("Could not create WorldList folder!");
            }
        }

        // Create Configuration Files
        systemConfig = new GameSystemConfig(plugin, "worlds.yml");
        gameManager = new GameManager();
    }
}
