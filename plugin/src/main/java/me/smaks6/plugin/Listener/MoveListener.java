package me.smaks6.plugin.Listener;

import me.smaks6.api.NokautEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.smaks6.plugin.Main.gracze;

public class MoveListener {

    @EventHandler
    public void moveEvent(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if (!gracze.get(p).equals(NokautEnum.STOI)) {
            double przed = event.getFrom().getY();
            double po = event.getTo().getY();
            double wynik = przed - po;
            if(wynik < 0){
                event.setCancelled(true);
            }

            if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()){
                event.setCancelled(true);
            }
        }
    }
}
