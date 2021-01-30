package com.gladurbad.anticheattestutil.handlers;

import com.gladurbad.anticheattestutil.AnticheatTestUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ClearItemHandler implements Runnable {

    private int ticks;
    private static BukkitTask task;

    public void start() {
        assert task == null : "ClearItemHandler has already started!";

        task = Bukkit.getScheduler().runTaskTimer(AnticheatTestUtil.instance, this, 0L, 1L);
    }

    public void stop() {
        if (task == null) return;

        task.cancel();
        task = null;
    }

    @Override
    public void run() {
        ++ticks;

        if (ticks % 20 == 0) {
            for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                }
            }
        }
    }

    public int getTicks() {
        return ticks;
    }
}
