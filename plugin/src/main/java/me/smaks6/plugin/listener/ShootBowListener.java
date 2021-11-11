package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ShootBowListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onBowShootEvent(EntityShootBowEvent event) {
        if(event.getEntity().getKiller() instanceof Player){
            Player p = event.getEntity().getKiller();
            if(!PlayerUtility.isNull(p)) {
                event.setCancelled(true);
                p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
            }
        }
    }
}
