package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void onHpRegenerationEvent(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if(!PlayerUtility.isNull(p)){
                e.setCancelled(true);
            }
        }

    }
}
