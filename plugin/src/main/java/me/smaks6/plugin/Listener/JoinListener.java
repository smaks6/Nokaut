package me.smaks6.plugin.Listener;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

import static me.smaks6.plugin.Main.gracze;

public class JoinListener implements Listener {

    @EventHandler
    public void joinListenear(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        gracze.put(p, NokautEnum.STOI);

        List<Player> npc = new ArrayList<>();

        gracze.forEach((player, nokautEnum) -> {

            if(!nokautEnum.equals(NokautEnum.STOI)){
                npc.add(player);
            }

        });
        for (Player znokautowany : npc) {
            Pose.createNPC(znokautowany, p);
            p.hidePlayer(Main.getInstance(), znokautowany);
        }

    }
}
