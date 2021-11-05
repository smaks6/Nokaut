package me.smaks6.plugin.Listener;

import me.smaks6.plugin.utilities.PlayerUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void hpRegenerationEvent(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if(!PlayerUtilities.isNull(p)){
                e.setCancelled(true);
            }
        }

    }
}
