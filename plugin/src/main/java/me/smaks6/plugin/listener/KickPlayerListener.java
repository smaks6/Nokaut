package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.ChatUtility;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.PoseUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public final class KickPlayerListener implements Listener {

    @EventHandler
    public void onKickEvent(PlayerKickEvent event) {
        Player p = event.getPlayer();

        if(PlayerUtility.isNull(p))return;

        if(PlayerUtility.getState(p).equals(Nokaut.CARRY) && event.getReason().contains("Cannot interact with self!")) {
            event.setCancelled(true);
            ChatUtility.sendDenyMessage(p);
        }

        if(!PlayerUtility.isNull(p)) {
            p.setHealth(0);
        }

        if(PlayerUtility.isNull(p))return;

        if(PlayerUtility.getState(p).equals(Nokaut.CARRY)){
            PlayerUtility.setState(p, Nokaut.LAY);
            Player vehicle = (Player) p.getVehicle();
            vehicle.getPassengers().clear();
            PoseUtility.changeGameMode(p, null, false);
        }

        if(!p.getPassengers().isEmpty()){
            Player knockedPlayer = (Player) p.getPassengers().get(0);
            PlayerUtility.setState(knockedPlayer, Nokaut.LAY);
            p.getPassengers().clear();
            PoseUtility.changeGameMode(knockedPlayer, p, false);
        }

        PlayerUtility.unSet(p);

    }
}
