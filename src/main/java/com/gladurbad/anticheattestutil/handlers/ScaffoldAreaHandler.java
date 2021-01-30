package com.gladurbad.anticheattestutil.handlers;

import com.gladurbad.anticheattestutil.AnticheatTestUtil;
import com.gladurbad.anticheattestutil.util.BoundingBox;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ScaffoldAreaHandler implements Listener {

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final BoundingBox scaffoldRegion = new BoundingBox(
                234D, Double.NEGATIVE_INFINITY, 164D, 293D, Double.POSITIVE_INFINITY, 208D
        );

        final BoundingBox playerBox = new BoundingBox(
                event.getTo().toVector().add(new Vector(0.4, 2, 0.4)),
                event.getTo().toVector().add(new Vector(-0.4, 0, -0.4))
        );

        final boolean intersects = playerBox.intersectsWith(scaffoldRegion);



        if (intersects) {
            event.getPlayer().getInventory().setItem(0, new ItemStack(Material.WOOL, 5, (byte) 14));
        }
    }
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        event.getPlayer().getInventory().setItem(0, new ItemStack(Material.WOOL, 5, (byte) 14));
        final Block block = event.getBlock();

        if (block == null || block.getType() == Material.AIR) return;
        final BoundingBox scaffoldRegion = new BoundingBox(
            234D, Double.NEGATIVE_INFINITY, 164D, 293D, Double.POSITIVE_INFINITY, 208D
        );

        final BoundingBox blockBox = new BoundingBox(
                block.getLocation().getX() - 0.5,
                block.getLocation().getY(),
                block.getLocation().getZ() - 0.5,
                block.getLocation().getX() + 0.5,
                block.getLocation().getY() + 1,
                block.getLocation().getZ() - 0.5
        );

        final boolean intersects = blockBox.intersectsWith(scaffoldRegion);

        if (intersects) {
            Bukkit.getServer().getScheduler().runTaskLater(AnticheatTestUtil.instance, () -> block.setType(Material.AIR), 50L);
        }
    }
}
