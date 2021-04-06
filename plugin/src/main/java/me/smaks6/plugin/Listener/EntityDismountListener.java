package me.smaks6.plugin.Listener;

import me.smaks6.api.NokautEnum;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

import static me.smaks6.plugin.Main.gracze;

public class EntityDismountListener implements Listener {

    @EventHandler
    public void enityDismount(EntityDismountEvent event) {
        if(!(event.getEntity() instanceof Player))return;
        Player p = (Player) event.getEntity();
        NokautEnum hashmap = gracze.get(p);
        if(hashmap.equals(NokautEnum.NIES)) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        }

    }
}
