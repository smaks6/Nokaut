package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.ChatUtility;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public final class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if(!PlayerUtility.isNull(p)) {
            event.setCancelled(true);
            ChatUtility.sendDenyMessage(p);
        }
    }

}
