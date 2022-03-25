package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SwapHandItemListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerSwapHandItemsEvent event) {
        HumanEntity human = event.getPlayer();
        if(!PlayerUtility.isNull((Player) human)) {
            event.setCancelled(true);
        }
    }
}
