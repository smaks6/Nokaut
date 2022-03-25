package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class InventoryMoveItemListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(InventoryClickEvent event) {
        HumanEntity human = event.getWhoClicked();
        if(human instanceof Player){
            if(!PlayerUtility.isNull((Player) human)) {
                event.setCancelled(true);
            }
        }
    }
}
