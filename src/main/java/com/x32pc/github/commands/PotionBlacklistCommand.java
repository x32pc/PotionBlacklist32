package com.x32pc.github.commands;

import com.x32pc.github.PotionBlacklist32;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PotionBlacklistCommand implements CommandExecutor {

    private final PotionBlacklist32 potionBlacklist32;

    public PotionBlacklistCommand(PotionBlacklist32 main) {
        this.potionBlacklist32 = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Use command in the following way: /potion blacklist <player>");
            return true;
        }

        if(!sender.hasPermission("potion.blacklist")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        String playerName = args[1];
        String playerUUID = Bukkit.getPlayer(playerName).getUniqueId().toString();

        if (potionBlacklist32.databaseManager.isBlacklisted(playerUUID)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', potionBlacklist32.getConfig().getString("messages.potion.on-blacklist")).replace("%player_name%", playerName));
        } else {
            potionBlacklist32.databaseManager.addToBlacklist(playerUUID);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', potionBlacklist32.getConfig().getString("messages.potion.added")).replace("%player_name%", playerName));
        }

        return true;
    }
}
