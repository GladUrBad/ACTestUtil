package com.gladurbad.anticheattestutil.commands;

import com.gladurbad.anticheattestutil.handlers.MedusaAlertHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("pvp")) {
                final Player player = (Player) sender;

                player.getInventory().clear();
                player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                player.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                player.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                player.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                player.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
                player.getInventory().getItem(0).addEnchantment(Enchantment.DAMAGE_ALL, 2);
                player.getInventory().getItem(0).addEnchantment(Enchantment.FIRE_ASPECT, 1);

                player.getInventory().setItem(1, new Potion(PotionType.FIRE_RESISTANCE, 2).toItemStack(3));
                player.getInventory().setItem(2, new Potion(PotionType.SPEED, 2).toItemStack(3));

                for (int i = 1; i <= 35; i++) {
                    if (player.getInventory().getItem(i) == null) {
                        player.getInventory().setItem(i, new Potion(PotionType.INSTANT_HEAL, 2).splash().toItemStack(1));
                    }
                }
                return true;
            } else if (command.getName().equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.GREEN + "Medusa Test Server Commands:");
                sender.sendMessage(ChatColor.GREEN + "/pvp : Gives you PvP items.");
                sender.sendMessage(ChatColor.GREEN + "/invclear : Clears your inventory.");
                sender.sendMessage(ChatColor.GREEN + "/warp <warpName> : Teleport to an area of the map.");
                sender.sendMessage(ChatColor.GREEN + "/colour <chatColor> : Change the colour of your alerts.");
                sender.sendMessage(ChatColor.GREEN + "/spawn : Teleports you to spawn.");
                sender.sendMessage(ChatColor.GREEN + "/alerts : Toggles Medusa AntiCheat alerts.");
                return true;
            } else if (command.getName().equalsIgnoreCase("invclear")) {
                for (int i = 1; i < ((Player) sender).getInventory().getSize(); i++) {
                    ((Player) sender).getInventory().setItem(i, new ItemStack(Material.AIR));
                }
                sender.sendMessage(ChatColor.GREEN + "Your inventory has been cleared!");
                return true;
            } else if (command.getName().equalsIgnoreCase("spawn")) {
                ((Player) sender).teleport(new Location(((Player) sender).getWorld(), 211, 62, 162));
                sender.sendMessage(ChatColor.GREEN + "You have been teleported to spawn!");
                return true;
            } else if (command.getName().equalsIgnoreCase("colour")) {
                switch (args.length) {
                    case 0:
                        sender.sendMessage(ChatColor.GREEN + "Colour options for Medusa alerts:");
                        for (ChatColor color : ChatColor.values()) {
                            sender.sendMessage(color + color.name().toLowerCase());
                        }
                        return true;
                    case 1:
                        final String chatColor = args[0];
                        for (ChatColor color : ChatColor.values()) {
                            if (color.name().toLowerCase().equalsIgnoreCase(chatColor)) {
                                sender.sendMessage(ChatColor.GREEN + "Set colour to " + color + color.name());
                                MedusaAlertHandler.getAlertColoursMap().put(((Player) sender).getUniqueId(), color);
                                return true;
                            }
                        }
                    default:
                        sender.sendMessage(ChatColor.RED + "Invalid colour!");
                        return false;
                }
            } else if (command.getName().equalsIgnoreCase("alerts")) {
                final boolean alerts = MedusaAlertHandler.getExempt().contains(((Player) sender).getUniqueId());

                if (!alerts) {
                    MedusaAlertHandler.getExempt().add(((Player) sender).getUniqueId());
                    sender.sendMessage(ChatColor.RED + "Turned off your alerts.");
                    return true;
                } else {
                    MedusaAlertHandler.getExempt().remove(((Player) sender).getUniqueId());
                    sender.sendMessage(ChatColor.GREEN + "Turned on your alerts.");
                    return true;
                }
            }
         }
        return false;
    }
}
