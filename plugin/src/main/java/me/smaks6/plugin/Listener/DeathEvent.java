package me.smaks6.plugin.Listener;

import me.smaks6.plugin.pose.Pose;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Pose.stop(p);
    }
}
