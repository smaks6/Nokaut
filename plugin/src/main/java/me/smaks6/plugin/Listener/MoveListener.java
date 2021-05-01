package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.smaks6.plugin.Main.gracze;

public class MoveListener implements Listener {

    @EventHandler
    public void moveEvent(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if (!gracze.get(p).equals(NokautEnum.STOI)) {
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
