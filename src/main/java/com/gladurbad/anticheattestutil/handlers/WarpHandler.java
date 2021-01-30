package com.gladurbad.anticheattestutil.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class WarpHandler implements CommandExecutor {

    private static final HashMap<String, Location> warps = new HashMap<>();

    public WarpHandler() {
        final World world = Bukkit.getWorld("world");
        warps.put("Combat", new Location(world, 162, 62, 162));
        warps.put("Speed", new Location(world, 254, 62, 124));
        warps.put("Step", new Location(world, 209, 62, 139));
        warps.put("NoFall", new Location(world, 212, 62, 140));
        warps.put("Scaffold", new Location(world, 253, 57, 181));
        warps.put("Jesus", new Location(world, 234, 62, 187));
        warps.put("Phase", new Location(world, 256, 62, 234));
        warps.put("Other", new Location(world, 188, 62, 212));
        warps.put("SpeedTest", new Location(world, 211, 62 ,101));
    }
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("warp")) {
                if (args.length == 1) {
                    final String warpName = args[0];

                    for (String key : warps.keySet()) {
                        if (warpName.toLowerCase().equals(key.toLowerCase())) {
                            ((Player) sender).teleport(warps.get(key));
                            sender.sendMessage(ChatColor.GREEN + "Teleported to " + key + " warp!");
                            return true;
                        }
                    }
                    sender.sendMessage(ChatColor.RED + "Invalid warp name!");
                    return false;
                } else if (args.length == 0) {
                    sender.sendMessage(ChatColor.GREEN + "Medusa AntiCheat Test Server Warps:");
                    sender.sendMessage("");
                    for (String warpName : warps.keySet()) {
                        sender.sendMessage(ChatColor.GREEN + warpName);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
