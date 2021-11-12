package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickPlayerListenear implements Listener {

    @EventHandler
    public void onKickEvent(PlayerKickEvent event) {
        Player p = event.getPlayer();

        if(PlayerUtility.isNull(p))return;

        if(PlayerUtility.getState(p).equals(Nokaut.CARRY) && event.getReason().contains("Cannot interact with self!")) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        }

    }
}
