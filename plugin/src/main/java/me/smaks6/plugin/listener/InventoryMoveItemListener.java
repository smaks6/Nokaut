package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public final class InventoryMoveItemListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(!PlayerUtility.isNull(p)) {
            event.setCancelled(true);
        }
    }
}
