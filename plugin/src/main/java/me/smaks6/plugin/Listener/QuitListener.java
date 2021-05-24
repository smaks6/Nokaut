package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.Nokaut;
import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void wychodzi(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        if(!PlayerUtilities.isNull(p)) {
            p.setHealth(0);
        }

        if(PlayerUtilities.isNull(p))return;

        if(PlayerUtilities.getEnum(p).equals(Nokaut.CARRY)){
            PlayerUtilities.setEnum(p, Nokaut.LAY);
            Player vehicle = (Player) p.getVehicle();
            vehicle.getPassengers().clear();
            Pose.changegamemode(p, null, false);
        }

        if(!p.getPassengers().isEmpty()){
            Player znokautowany = (Player) p.getPassengers().get(0);
            PlayerUtilities.setEnum(znokautowany, Nokaut.LAY);
            p.getPassengers().clear();
            Pose.changegamemode(znokautowany, p, false);
        }

        PlayerUtilities.unSet(p);

    }
}
