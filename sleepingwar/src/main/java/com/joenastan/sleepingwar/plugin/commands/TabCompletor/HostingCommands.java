package com.joenastan.sleepingwar.plugin.commands.TabCompletor;

import com.joenastan.sleepingwar.plugin.SleepingWarsPlugin;
import com.joenastan.sleepingwar.plugin.utility.GameSystemConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class HostingCommands implements TabCompleter {

    private static final GameSystemConfig systemConfig = SleepingWarsPlugin.getGameSystemConfig();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> hints = new ArrayList<String>();
            hints.add("host");
            hints.add("join");
            hints.add("start");
            hints.add("leave");
            return hints;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("host")) {
                return systemConfig.getAllWorldName();
            } else if (args[0].equalsIgnoreCase("join")) {
                List<String> roomNames = new ArrayList<String>();
                roomNames.addAll(SleepingWarsPlugin.getGameManager().getAllRoom().keySet());
                return roomNames;
            }
        }
        return null;
    }

}
