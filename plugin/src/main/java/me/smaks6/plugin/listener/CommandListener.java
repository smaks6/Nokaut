package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.ChatUtility;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerExecuteCommandEvent(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        if(!PlayerUtility.isNull(p)) {
            if (!event.getMessage().toLowerCase().startsWith("/zo") && !event.getMessage().toLowerCase().startsWith("/zginodrazu") && !event.getMessage().toLowerCase().startsWith("/deathnow") && !event.getMessage().toLowerCase().startsWith("/dn")) {
                event.setCancelled(true);
                ChatUtility.sendDenyMessage(p);
            }
        }
    }
}
