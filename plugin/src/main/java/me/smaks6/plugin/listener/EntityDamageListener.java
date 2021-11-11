package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.NokautUtility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageEvent(EntityDamageEvent event) {

        if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            return;
        }

        if (NokautUtility.doNokaut(event.getEntity(), event.getFinalDamage())) {
            event.setCancelled(true);
        }
    }
}
