package com.gladurbad.anticheattestutil.handlers;

import com.gladurbad.anticheattestutil.skidding.stackoverflow.StringSimilarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.BatchUpdateException;
import java.util.*;

public class ChatHandler implements Listener {

    private static final List<String> blackListedWords = new ArrayList<>();
    private static final Map<UUID, Long> lastChatTimes = new HashMap<>();
    private static final Map<UUID, String> lastChats = new HashMap<>();
    private static final Map<UUID, Integer> chatSimilarityVerbose = new HashMap<>();

    public ChatHandler() {
        blackListedWords.addAll(Arrays.asList(
                "nigger",
                "fuck",
                "shit",
                "bitch",
                "asshole",
                "chink",
                "beaner",
                "anal",
                "anus",
                "ass",
                "ballsack",
                "boner",
                "fag",
                "tranny",
                "gay",
                "dick",
                "dildo",
                "nigga",
                "hell",
                "homo",
                "sex",
                "smegma",
                "whore",
                "vagina",
                "pussy",
                "cunt"
        ));
    }
    @EventHandler
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        final String chat = event.getMessage();
        final String lastChat = lastChats.getOrDefault(event.getPlayer().getUniqueId(), "");

        if (!lastChat.equals("")) {
            final double chatSimilarity = StringSimilarity.similarity(chat, lastChat);

            if (chatSimilarity == 1.0) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot say the same message!");
                return;
            } else if (chatSimilarity > 0.75) {
                final int verbose = chatSimilarityVerbose.getOrDefault(event.getPlayer().getUniqueId(), 0);
                chatSimilarityVerbose.put(event.getPlayer().getUniqueId(), verbose + 1);
                if (chatSimilarityVerbose.get(event.getPlayer().getUniqueId()) > 3) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(ChatColor.RED + "You cannot say the same message!");
                    return;
                }
            } else {
                final int verbose = chatSimilarityVerbose.getOrDefault(event.getPlayer().getUniqueId(), 0);
                chatSimilarityVerbose.put(event.getPlayer().getUniqueId(), verbose > 0 ? verbose - 1 : 0);
            }
        }

        lastChats.put(event.getPlayer().getUniqueId(), chat);

        final long lastChatTime = lastChatTimes.getOrDefault(event.getPlayer().getUniqueId(), 0L);

        if (lastChatTime != -0L) {
            final long now = System.currentTimeMillis();
            if (now - lastChatTime < 1200L) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are chatting too fast!");
                return;
            }
        }

        lastChatTimes.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());

        final String reformattedChat = chat
                .replaceAll("\\$", "S")
                .replaceAll("1", "i")
                .replaceAll("4,", "a")
                .replaceAll("3", "e")
                .replaceAll("@", "a")
                .replaceAll("5", "s")
                .replaceAll("9", "g")
                .replaceAll("0", "o")
                .replaceAll(" ", "")
                .toLowerCase();

        for (String string : blackListedWords) {
            if (reformattedChat.contains(string)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Watch your language!");
                return;
            }
        }
    }
}
