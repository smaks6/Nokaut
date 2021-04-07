package me.smaks6.plugin.Listener;

import me.smaks6.api.NokautEnum;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.smaks6.plugin.Main.gracze;

public class QuitListener implements Listener {

    @EventHandler
    public void wychodzi(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        NokautEnum hashmap = gracze.get(p);
        if(!hashmap.equals(NokautEnum.STOI)) {
            p.setHealth(0);
        }

        if(!p.getPassengers().isEmpty()){
            Player znokautowany = (Player) p.getPassengers().get(0);
            gracze.replace(znokautowany, NokautEnum.LEZY);
            p.getPassengers().clear();
            Pose.changegamemode(znokautowany, p, false);
        }

        gracze.remove(p);

    }
}
