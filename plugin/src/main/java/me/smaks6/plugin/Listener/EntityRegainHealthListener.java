package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import static me.smaks6.plugin.Main.gracze;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void regeneracjaHP(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (!gracze.get(p).equals(NokautEnum.STOI)) {
                e.setCancelled(true);
            }
        }

    }
}
