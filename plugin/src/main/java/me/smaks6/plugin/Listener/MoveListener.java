package me.smaks6.plugin.Listener;

import me.smaks6.plugin.utilities.PlayerUtilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void moveEvent(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if (!PlayerUtilities.isNull(p)) {
            double przed = event.getFrom().getY();
            double po = event.getTo().getY();
            double wynik = przed - po;
            if(wynik < 0){
                event.setCancelled(true);
                p.teleport(event.getFrom());
            }

            if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) event.setCancelled(true); return;
        }
    }
}
