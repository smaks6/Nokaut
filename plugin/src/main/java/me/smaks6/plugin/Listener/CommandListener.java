package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import static me.smaks6.plugin.Main.gracze;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void commandblock(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        NokautEnum hashmap = gracze.get(p);
        if(!hashmap.equals(NokautEnum.STOI)) {
            if (!event.getMessage().toLowerCase().startsWith("/zo") && !event.getMessage().toLowerCase().startsWith("/zginodrazu") && !event.getMessage().toLowerCase().startsWith("/deathnow") && !event.getMessage().toLowerCase().startsWith("/dn")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
            }
        }
    }
}
