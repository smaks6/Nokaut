package me.smaks6.plugin.listener;

import me.smaks6.plugin.objects.ArmorStandNokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.service.CitizensService;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamageByEntityEvent(EntityDamageByEntityEvent event){

        if(event.getEntity() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getEntity();
            Player player = Bukkit.getPlayer(ArmorStandNokaut.getData(armorStand));
            if(player == null)return;
            if(player.isOnline()){
                player.damage(2, event.getDamager());
            }
        }

        if(CitizensService.isNpc(event.getDamager()))return;

        if(event.getDamager() instanceof Player){
            if(!PlayerUtility.isNull((Player) event.getDamager())){
                event.setCancelled(true);
            }
        }
    }
}
