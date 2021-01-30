package com.gladurbad.anticheattestutil.handlers;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), 211, 62, 162));
    }
}
