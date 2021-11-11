package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if (!PlayerUtility.isNull(p)) {
            double before = event.getFrom().getY();
            double after = event.getTo().getY();
            double result = before - after;
            if(result < 0){
                event.setCancelled(true);
                p.teleport(event.getFrom());
            }

            if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) event.setCancelled(true); return;
        }
    }
}
