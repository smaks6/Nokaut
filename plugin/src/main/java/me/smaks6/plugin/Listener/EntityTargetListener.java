package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import static me.smaks6.plugin.Main.gracze;

public class EntityTargetListener implements Listener {

    @EventHandler
    public void target(EntityTargetEvent event) {
        if(event.getTarget() instanceof Player){
            Player player = (Player) event.getTarget();

            if(!gracze.get(player).equals(NokautEnum.STOI)) {
                event.setTarget(null);
            }
        }
    }
}
