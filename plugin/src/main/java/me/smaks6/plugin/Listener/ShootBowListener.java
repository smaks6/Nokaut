package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import static me.smaks6.plugin.Main.gracze;

public class ShootBowListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void bowshoot(EntityShootBowEvent event) {
        if(event.getEntity().getKiller() instanceof Player){
            Player p = event.getEntity().getKiller();
            NokautEnum hashmap = gracze.get(p);
            if(!hashmap.equals(NokautEnum.STOI)) {
                event.setCancelled(true);
                p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
            }
        }
    }
}
