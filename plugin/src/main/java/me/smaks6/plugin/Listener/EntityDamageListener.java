package me.smaks6.plugin.Listener;

import me.smaks6.plugin.utilities.NokautUtilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void entityDamageEvent(EntityDamageEvent event) {

        if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            return;
        }

        if (NokautUtilities.doNokaut(event.getEntity(), event.getFinalDamage())) {
            event.setCancelled(true);
        }
    }
}
