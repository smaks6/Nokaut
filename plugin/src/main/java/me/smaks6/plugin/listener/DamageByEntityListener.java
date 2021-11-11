package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.service.CitizensService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamageByEntityEvent(EntityDamageByEntityEvent event){

        if(CitizensService.isNpc(event.getDamager()))return;

        if(event.getDamager() instanceof Player){
            if(!PlayerUtility.isNull((Player) event.getDamager())){
                event.setCancelled(true);
            }
        }
    }
}
