package me.smaks6.plugin.Listener;

import me.smaks6.api.NokautEnum;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import static me.smaks6.plugin.Main.gracze;

public class PickupItemListener implements Listener {

    @EventHandler
    public void wezitem(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        NokautEnum hashmap = gracze.get(p);
        if(!hashmap.equals(NokautEnum.STOI)) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        }

    }
}
