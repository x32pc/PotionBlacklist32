package com.x32pc.github.event;

import com.x32pc.github.PotionBlacklist32;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class CheckDrinkEvent implements Listener {

    private final PotionBlacklist32 potionBlacklist32;

    public CheckDrinkEvent(PotionBlacklist32 main) {
        this.potionBlacklist32 = main;
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.getType() == Material.POTION) {
            String message = ChatColor.translateAlternateColorCodes('&', potionBlacklist32.getConfig().getString("messages.potion.is-blacklisted-screen"));
            if(potionBlacklist32.databaseManager.isBlacklisted(p.getUniqueId().toString())) {
                sendActionBarMessage(p, message);
                event.setCancelled(true);
            }
        }
    }

    public void sendActionBarMessage(Player player, String message) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + " actionbar {\"text\":\"" + message + "\"}");
    }
}
