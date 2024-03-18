package com.x32pc.github.event;

import com.x32pc.github.PotionBlacklist32;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.inventory.ItemStack;

public class CheckLingeringEvent implements Listener {

    private final PotionBlacklist32 potionBlacklist32;

    public CheckLingeringEvent(PotionBlacklist32 main) {
        this.potionBlacklist32 = main;
    }


    @EventHandler
    public void onLingeringPotionSplash(LingeringPotionSplashEvent event) {
        Player p = (Player) event.getEntity().getShooter();

        if (potionBlacklist32.databaseManager.isBlacklisted(p.getUniqueId().toString())) {
            String message = ChatColor.translateAlternateColorCodes('&', potionBlacklist32.getConfig().getString("messages.potion.is-blacklisted-screen"));
            sendActionBarMessage(p, message);
            ThrownPotion potion = event.getEntity();
            ItemStack potionItem = potion.getItem();
            p.getInventory().addItem(potionItem);
            event.setCancelled(true);
            }
         }

    public void sendActionBarMessage(Player player, String message) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title " + player.getName() + " actionbar {\"text\":\"" + message + "\"}");
    }
}
