package com.x32pc.github.commands;

import com.x32pc.github.PotionBlacklist32;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PotionUnblacklistCommand implements CommandExecutor {

    private final PotionBlacklist32 potionBlacklist32;

    public PotionUnblacklistCommand(PotionBlacklist32 main) {
        this.potionBlacklist32 = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Use command in the following way: /potion unblacklist <player>");
            return true;
        }

        if(!sender.hasPermission("potion.unblacklist")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        String playerName = args[1];
        String playerUUID = Bukkit.getPlayer(playerName).getUniqueId().toString();

        if (potionBlacklist32.databaseManager.isBlacklisted(playerUUID)) {
            potionBlacklist32.databaseManager.removeFromBlacklist(playerUUID);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', potionBlacklist32.getConfig().getString("messages.potion.removed")).replace("%player_name%", playerName));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', potionBlacklist32.getConfig().getString("messages.potion.not-blacklisted")).replace("%player_name%", playerName));
            }

        return true;
    }
}
