package com.x32pc.github.commands;

import com.x32pc.github.PotionBlacklist32;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BaseCommand implements CommandExecutor {

    private final PotionBlacklist32 potionBlacklist32;

    public BaseCommand(PotionBlacklist32 main) {
        this.potionBlacklist32 = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length < 2) {
            for (String message : potionBlacklist32.getConfig().getStringList("messages.help"))
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }

        if(args[0].equalsIgnoreCase("blacklist")) {
            PotionBlacklistCommand potionBlacklistCommand = new PotionBlacklistCommand(potionBlacklist32);
            potionBlacklistCommand.onCommand(sender, command, label, args);
        } else if(args[0].equalsIgnoreCase("unblacklist")) {
            PotionUnblacklistCommand potionUnblacklistCommand = new PotionUnblacklistCommand(potionBlacklist32);
            potionUnblacklistCommand.onCommand(sender, command, label, args);
        }
        return true;
    }
}
