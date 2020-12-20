package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.smaks6.nokaut.Main.gracze;

import ru.armagidon.poseplugin.api.PosePluginAPI;
import ru.armagidon.poseplugin.api.events.StopPosingEvent;
import ru.armagidon.poseplugin.api.player.PosePluginPlayer;

public class BlockInNokaut implements Listener{
	
	@EventHandler(priority = EventPriority.LOW)
	public void kladz(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void niszcz(BlockBreakEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}
		
	}
	

	
	@EventHandler(priority = EventPriority.LOWEST)
	public void niewstawaj(StopPosingEvent event) {
		Player p = event.getPlayer().getHandle();
		String hashmap = gracze.get(p.getName());
		if(hashmap.equals("nies")) {
			event.setCancelled(false);
			return;
		}
		event.setCancelled(!hashmap.equals("stoi"));
		

		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void chestitd(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}
		
	}

	@EventHandler
	public void bowshoot(EntityShootBowEvent event) {
		if(event.getEntity().getKiller() instanceof Player){
			Player p = event.getEntity().getKiller();
			String hashmap = gracze.get(p.getName());
			if(!hashmap.equals("stoi")) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
			}else {
				event.setCancelled(false);
			}
		}
	}

	@EventHandler
	public void dropitem(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}

	}

	
	@EventHandler
	public void wchodzi(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		gracze.put(p.getName(), "stoi");
		
	}
	
	@EventHandler
	public void wychodzi(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			p.setHealth(0);
		}
		gracze.remove(p.getName());
		
	}
	
	@EventHandler
	public void smierc(PlayerDeathEvent event) {
		Player p = event.getEntity();
		gracze.replace(p.getName(), "stoi");
		PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(p);
        posePluginPlayer.resetCurrentPose();
		
	}
}
