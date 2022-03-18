package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PoseUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathEvent implements Listener {
    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        Player p = event.getEntity();
        PoseUtility.stop(p);
    }
}
