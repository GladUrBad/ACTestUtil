package com.gladurbad.anticheattestutil.handlers;

import com.gladurbad.anticheattestutil.AnticheatTestUtil;
import com.gladurbad.anticheattestutil.util.Pair;
import com.gladurbad.anticheattestutil.util.SpeedTest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpeedTestHandler implements Listener {

    private static final Map<UUID, SpeedTest> speedTests = new HashMap<>();

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final SpeedTest speedTest = speedTests.getOrDefault(event.getPlayer().getUniqueId(), null);

        if (speedTest != null) {
            if (!speedTest.isAllowedToMove()) {
                event.getPlayer().teleport(event.getFrom());
            } else {
                if (speedTest.finishedTest()) {
                    final Pair<Double, Double> speedPct = speedTest.getSpeedPercentage();

                    final long ms = speedTest.getElapsedTime();
                    final String sps = String.format("%.2f", speedTest.getSpeedInSeconds());
                    final String spt = String.format("%.2f", speedTest.getSpeedInTicks());
                    final String spps = String.format("%.2f", speedTest.getSpeedPercentage().getKey());
                    final String sppt = String.format("%.2f", speedTest.getSpeedPercentage().getValue());

                    event.getPlayer().sendMessage(ChatColor.GREEN + "It took you " + ms + "ms to finish the SpeedTest.");
                    event.getPlayer().sendMessage(ChatColor.GREEN + "You moved " + sps + " blocks per second or");
                    event.getPlayer().sendMessage(ChatColor.GREEN + spt + " blocks per tick!");
                    event.getPlayer().sendMessage(ChatColor.GREEN + "You moved " + spps + "% / " + sppt + "% faster than Vanilla!");
                    speedTest.endTest();
                    speedTests.remove(event.getPlayer().getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                final Sign sign = (Sign) event.getClickedBlock().getState();
                final String[] lines = sign.getLines();

                if (lines[0].replaceAll(" ", "").equals("[SpeedTest]")) {
                    final String[] teleportLine = lines[1].replaceAll("/[^a-zA-Z]/g", "").split(" ");
                    final String[] teleportLine2 = lines[2].replaceAll("/[^a-zA-Z]/g", "").split(" ");
                    final String[] teleportLine3 = lines[3].replaceAll("/[^a-zA-Z]/g", "").split(" ");
                    final Location start = new Location(
                            event.getPlayer().getWorld(),
                            Double.parseDouble(teleportLine[0]),
                            Double.parseDouble(teleportLine[1]),
                            Double.parseDouble(teleportLine[2])
                    );
                    final Location end = new Location(
                            event.getPlayer().getWorld(),
                            Double.parseDouble(teleportLine2[0]),
                            Double.parseDouble(teleportLine2[1]),
                            Double.parseDouble(teleportLine2[2])
                    );
                    final Location reset = new Location(
                            event.getPlayer().getWorld(),
                            Double.parseDouble(teleportLine3[0]),
                            Double.parseDouble(teleportLine3[1]),
                            Double.parseDouble(teleportLine3[2])
                    );
                    speedTests.put(event.getPlayer().getUniqueId(), new SpeedTest(
                            event.getPlayer(),
                            start,
                            end,
                            reset
                    ));
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Beginning SpeedTest in 3 seconds...");
                    Bukkit.getServer().getScheduler().runTaskLater(AnticheatTestUtil.instance, () -> event.getPlayer().sendMessage(ChatColor.GREEN + "2..."), 20L);
                    Bukkit.getServer().getScheduler().runTaskLater(AnticheatTestUtil.instance, () -> event.getPlayer().sendMessage(ChatColor.GREEN + "1..."), 40L);
                    Bukkit.getServer().getScheduler().runTaskLater(AnticheatTestUtil.instance, () -> {
                        event.getPlayer().sendMessage(ChatColor.GREEN + "Go!");
                        speedTests.get(event.getPlayer().getUniqueId()).setAllowedToMove(true);
                    }, 60L);
                }
            }
        }
    }
}
