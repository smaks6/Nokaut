package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.service.CitizensListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.smaks6.plugin.Main.gracze;

public class DamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void stopdamage(EntityDamageByEntityEvent event){

        if(CitizensListener.isNpc(event.getEntity()))return;

        if(event.getDamager() instanceof Player){
            if(!gracze.get((Player) event.getDamager()).equals(NokautEnum.STOI)){
                event.setCancelled(true);
            }

            if(event.getEntity() instanceof Player){
                Player damager = (Player) event.getDamager();
                Player player  = (Player) event.getEntity();


                if(gracze.get(damager).equals(NokautEnum.STOI) && gracze.get(player).equals(NokautEnum.NIES)){
                    gracze.replace(player, NokautEnum.LEZY);
                }

            }
        }
    }
}
