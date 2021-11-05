package me.smaks6.plugin.Listener;

import me.smaks6.plugin.service.updatechecker;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class JoinListener implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        List<Player> npc = new ArrayList<>();

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(!PlayerUtilities.isNull(player)){
                npc.add(player);
            }
        }

        for (Player knockedPlayer : npc) {
            Pose.createNPC(knockedPlayer, p);
            p.hidePlayer(Main.getInstance(), knockedPlayer);
        }

        if(p.isOp()){
            new updatechecker(85152).getVersion(version -> {
                if(!Main.getInstance().getDescription().getVersion().equalsIgnoreCase(version)){
                    p.sendMessage(ChatColor.GOLD + "=======================================");
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.RED + "UPDATE!");
                    p.sendMessage(ChatColor.RED + "You do not have the current version of the knockout!");
                    p.sendMessage(ChatColor.RED + "Please update!");
                    p.sendMessage(ChatColor.RED + "Your version: " + ChatColor.GOLD + Main.getInstance().getDescription().getVersion() +
                            ChatColor.RED + " Latest Version: " + ChatColor.GOLD + version);
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.GOLD + "=======================================");
                }
            });
        }
    }
}
