package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.service.CitizensListener;
import me.smaks6.plugin.utilities.Runnables;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

import static me.smaks6.plugin.Main.gracze;

public class SneakListener implements Listener {

    @EventHandler
    public void sneakEvent(PlayerToggleSneakEvent e){
        Player p = e.getPlayer();
        if(!gracze.get(p).equals(NokautEnum.STOI)){
            e.setCancelled(true);
            return;
        }
        List<Entity> players = p.getNearbyEntities(2,2,2);
        if(players.isEmpty()){
            return;
        }

        for (Entity player : players) {
            if (CitizensListener.isNpc(player)) {
                return;
            }
            if(player instanceof Player){
                Player plist = (Player) player;
                NokautEnum hashmap = gracze.get(plist);
                if(hashmap.equals(NokautEnum.LEZY)){
                    Runnables.revievePlayer(p, plist);
                    break;
                }
            }
        }
        return;
    }
}
