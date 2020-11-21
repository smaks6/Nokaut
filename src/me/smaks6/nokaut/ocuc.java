package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ru.armagidon.poseplugin.api.PosePluginAPI;
import ru.armagidon.poseplugin.api.player.PosePluginPlayer;
import ru.armagidon.poseplugin.api.poses.EnumPose;
import ru.armagidon.poseplugin.api.poses.IPluginPose;
import ru.armagidon.poseplugin.api.poses.PoseBuilder;
import ru.armagidon.poseplugin.api.poses.options.EnumPoseOption;
import ru.armagidon.poseplugin.api.utils.npc.HandType;
import static me.smaks6.nokaut.Main.gracze;

public class ocuc implements Listener{

	@EventHandler
	public void Ocuc(EntityDamageByEntityEvent e) {
		Entity entityhp = e.getEntity();
		Entity entityd = e.getDamager();
		if(entityhp instanceof Player && entityd instanceof Player){
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			String hashmap = gracze.get(p.getName());
			String hashmapdamager = gracze.get(d.getName());
			if(hashmap == "chwila") {
				e.setCancelled(true);
				return;
			}
			if(hashmap == "nies") {
				gracze.replace(p.getName(), "lezy");
				Location dloc = d.getLocation();
				p.teleport(dloc);
                PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(p);
                IPluginPose pose = PoseBuilder.builder(EnumPose.LYING).option(EnumPoseOption.HANDTYPE, HandType.LEFT).build(p);
                posePluginPlayer.changePose(pose);
                e.setCancelled(true);
                p.leaveVehicle();
                return;
			}
			
			if(hashmapdamager != "stoi") {
				e.setCancelled(true);
				d.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
				return;
			}
			if(hashmap == "lezy"){
				gracze.replace(p.getName(), "stoi");
				p.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupplayer").replace("{player}", d.getName()));
				d.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupdamager").replace("{player}", p.getName()));
				PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(p);
                posePluginPlayer.resetCurrentPose();
                d.removePassenger(p);
                e.setCancelled(true);
			}
			
			
		}else if(entityhp instanceof Player) {
			Player p = (Player) e.getEntity();
			String hashmap = gracze.get(p.getName());
			if(hashmap == "lezy") {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}

		}else if(entityd instanceof Player){
			Player p = (Player) e.getDamager();
			String hashmap = gracze.get(p.getName());
			if(hashmap != "stoi") {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
			}
		}else {
			e.setCancelled(false);
		}
		
	}
}
