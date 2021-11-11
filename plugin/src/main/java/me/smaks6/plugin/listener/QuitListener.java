package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.utilities.PoseUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        if(!PlayerUtility.isNull(p)) {
            p.setHealth(0);
        }

        if(PlayerUtility.isNull(p))return;

        if(PlayerUtility.getEnum(p).equals(Nokaut.CARRY)){
            PlayerUtility.setEnum(p, Nokaut.LAY);
            Player vehicle = (Player) p.getVehicle();
            vehicle.getPassengers().clear();
            PoseUtility.changegamemode(p, null, false);
        }

        if(!p.getPassengers().isEmpty()){
            Player knockedPlayer = (Player) p.getPassengers().get(0);
            PlayerUtility.setEnum(knockedPlayer, Nokaut.LAY);
            p.getPassengers().clear();
            PoseUtility.changegamemode(knockedPlayer, p, false);
        }

        PlayerUtility.unSet(p);

    }
}
