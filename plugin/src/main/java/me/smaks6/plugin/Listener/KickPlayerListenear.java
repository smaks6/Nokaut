package me.smaks6.plugin.Listener;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class KickPlayerListenear implements Listener {

    @EventHandler
    public void kickEvent(PlayerKickEvent event) {
        Player p = event.getPlayer();

        if(PlayerUtilities.isNull(p))return;

        if(PlayerUtilities.getEnum(p).equals(Nokaut.CARRY) && event.getReason().contains("Cannot interact with self!")) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        }

    }
}
