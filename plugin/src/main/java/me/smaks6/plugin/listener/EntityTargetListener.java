package me.smaks6.plugin.listener;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetListener implements Listener {

    @EventHandler
    public void onSetEntityTargetEvent(EntityTargetEvent event) {
        if(event.getTarget() instanceof Player){
            Player player = (Player) event.getTarget();

            if(!PlayerUtility.isNull(player)){
                event.setTarget(null);
            }
        }
    }
}
