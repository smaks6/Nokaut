package me.smaks6.plugin.Listener;

import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class JoinListener implements Listener {

    @EventHandler
    public void joinListenear(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        List<Player> npc = new ArrayList<>();

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(!PlayerUtilities.isNull(player)){
                npc.add(player);
            }
        }

        for (Player znokautowany : npc) {
            Pose.createNPC(znokautowany, p);
            p.hidePlayer(Main.getInstance(), znokautowany);
        }
    }
}
