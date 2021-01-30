package com.gladurbad.anticheattestutil.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeleportSignHandler implements Listener {
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                final Sign sign = (Sign) event.getClickedBlock().getState();
                final String[] lines = sign.getLines();

                if (lines[0].replaceAll(" ", "").equals("[TeleportSign]")) {
                    final String[] teleportLine = lines[1].replaceAll("/[^a-zA-Z]/g", "").split(" ");
                    final Location tpLocation = new Location(
                            event.getPlayer().getWorld(),
                            Double.parseDouble(teleportLine[0]),
                            Double.parseDouble(teleportLine[1]),
                            Double.parseDouble(teleportLine[2]),
                            event.getPlayer().getEyeLocation().getYaw(),
                            event.getPlayer().getEyeLocation().getPitch()
                    );
                    event.getPlayer().teleport(tpLocation);
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Teleported you to " + lines[2] + "!");
                }
            }
        }
    }
}
