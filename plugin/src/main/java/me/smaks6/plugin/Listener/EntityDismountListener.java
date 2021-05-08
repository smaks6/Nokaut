package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.Nokaut;
import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityDismountListener implements Listener {

    @EventHandler
    public void enityDismount(EntityDismountEvent event) {
        if(!(event.getEntity() instanceof Player) )return;
        Player p = (Player) event.getEntity();
        if(!p.isOnline())return;

        if(PlayerUtilities.isNull(p))return;

        if(PlayerUtilities.getEnum(p).equals(Nokaut.CARRY)){
            event.setCancelled(true);
            p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        }

    }
}
