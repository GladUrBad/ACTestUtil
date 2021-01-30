package com.gladurbad.anticheattestutil.handlers;

import com.gladurbad.anticheattestutil.util.Pair;
import com.gladurbad.api.check.MedusaCheck;
import com.gladurbad.api.listener.MedusaFlagEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class ViolationScoreboardHandler implements Listener {

    private static final Map<UUID, Scoreboard> scoreboards = new HashMap<>();
    private static final Map<UUID, HashMap<String, Integer>> violations = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (!scoreboards.containsKey(event.getPlayer().getUniqueId())) {
            final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            final Objective objective = scoreboard.registerNewObjective("Check Violations", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.AQUA + "Check Violations");

            objective.getScore(ChatColor.GREEN + "Inventory").setScore(0);
            objective.getScore(ChatColor.GREEN + "NoSlow").setScore(0);
            objective.getScore(ChatColor.GREEN + "Timer").setScore(0);
            objective.getScore(ChatColor.GREEN + "Hand").setScore(0);
            objective.getScore(ChatColor.GREEN + "Scaffold").setScore(0);
            objective.getScore(ChatColor.GREEN + "FastClimb").setScore(0);
            objective.getScore(ChatColor.GREEN + "Velocity").setScore(0);
            objective.getScore(ChatColor.GREEN + "HitBox").setScore(0);
            objective.getScore(ChatColor.GREEN + "Reach").setScore(0);
            objective.getScore(ChatColor.GREEN + "AimAssist").setScore(0);
            objective.getScore(ChatColor.GREEN + "AutoClicker").setScore(0);
            objective.getScore(ChatColor.GREEN + "KillAura").setScore(0);
            objective.getScore(ChatColor.GREEN + "Fly").setScore(0);
            objective.getScore(ChatColor.GREEN + "Motion").setScore(0);
            objective.getScore(ChatColor.GREEN + "Speed").setScore(0);
            objective.getScore(ChatColor.GREEN + "Protocol").setScore(0);
            objective.getScore(ChatColor.GREEN + "Jesus").setScore(0);

            scoreboards.put(event.getPlayer().getUniqueId(), scoreboard);
            violations.put(event.getPlayer().getUniqueId(), new HashMap<>());

            event.getPlayer().setScoreboard(scoreboard);
        }
    }

    @EventHandler
    public void onMedusaFlag(final MedusaFlagEvent event) {
        final Scoreboard scoreboard = scoreboards.getOrDefault(event.getPlayer().getUniqueId(), null);

        final String checkName = event.getCheck().getCheckInfo().name().split(" ")[0];

        if (!violations.containsKey(event.getPlayer().getUniqueId())) return;

        violations.get(event.getPlayer().getUniqueId()).put(checkName, violations.get(event.getPlayer().getUniqueId()).getOrDefault(checkName, 0) + 1);
        final int vl = violations.get(event.getPlayer().getUniqueId()).get(checkName);

        if (scoreboard != null) {
            final Objective objective = scoreboard.getObjective("Check Violations");

            if (objective != null) {
                objective.getScore(ChatColor.GREEN + checkName).setScore(vl);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        scoreboards.remove(event.getPlayer().getUniqueId());
        violations.remove(event.getPlayer().getUniqueId());
    }
}
