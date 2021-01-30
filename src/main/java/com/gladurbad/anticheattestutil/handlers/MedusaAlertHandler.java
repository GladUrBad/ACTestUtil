package com.gladurbad.anticheattestutil.handlers;

import com.gladurbad.api.listener.MedusaSendAlertEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.*;

public class MedusaAlertHandler implements Listener {

    private static final Map<UUID, ChatColor> alertColours = new HashMap<>();
    private static final Set<UUID> exempt = new HashSet<>();

    @EventHandler
    public void onMedusaSendAlert(final MedusaSendAlertEvent event) {
        event.setCancelled(true);

        if (exempt.contains(event.getPlayer().getUniqueId())) return;

        final String name = event.getCheck().getCheckInfo().name()
                .replaceAll("[() ]", "");

        final String info = event.getInfo();

        final ChatColor c = alertColours.getOrDefault(event.getPlayer().getUniqueId(), ChatColor.RED);
        final ChatColor g = ChatColor.GRAY;
        final String flagFormat2 = c + "MDS" + g + ": %player% failed " + c + name + " " + g + info + c + " VL: " + event.getCheck().getVl();

        event.getPlayer().sendMessage(flagFormat2.replaceAll("%player%", event.getPlayer().getName()));
    }

    public static Map<UUID, ChatColor> getAlertColoursMap() {
        return alertColours;
    }

    public static Set<UUID> getExempt() {
        return exempt;
    }
}
