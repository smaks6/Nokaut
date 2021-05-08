package me.smaks6.plugin.Listener;

import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.service.CitizensListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void stopdamage(EntityDamageByEntityEvent event){

        if(CitizensListener.isNpc(event.getEntity()))return;

        if(event.getDamager() instanceof Player){
            if(!PlayerUtilities.isNull((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
}
