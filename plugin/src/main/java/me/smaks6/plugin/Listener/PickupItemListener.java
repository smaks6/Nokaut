package me.smaks6.plugin.Listener;

import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupItemListener implements Listener {

    @EventHandler
    public void wezitem(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        if(!PlayerUtilities.isNull(p)) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        }
    }
}
