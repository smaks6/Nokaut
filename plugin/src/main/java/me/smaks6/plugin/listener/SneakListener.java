package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.service.CitizensService;
import me.smaks6.plugin.utilities.Runnables;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

public final class SneakListener implements Listener {

    @EventHandler
    public void onSneakEvent(PlayerToggleSneakEvent e){
        Player p = e.getPlayer();
        if(!PlayerUtility.isNull(p)){
            e.setCancelled(true);
            return;
        }
        List<Entity> players = p.getNearbyEntities(2,2,2);
        if(players.isEmpty()){
            return;
        }

        for (Entity player : players) {
            if (CitizensService.isNpc(player)) {
                return;
            }
            if(player instanceof Player){
                Player plist = (Player) player;
                if(!PlayerUtility.isNull(plist)){
                    if(PlayerUtility.getState(plist).equals(Nokaut.LAY)){
                        Runnables.revivePlayer(p, plist);
                        break;
                    }
                }
            }
        }
        return;
    }
}
