package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import static me.smaks6.nokaut.Main.gracze;

public class BlockInNokaut implements Listener{
	
	@EventHandler(priority = EventPriority.LOW)
	public void kladz(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void niszcz(BlockBreakEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}
		
	}

	
	@EventHandler(priority = EventPriority.LOWEST)
	public void chestitd(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
		}
		
	}

	@EventHandler(priority = EventPriority.LOW)
	public void bowshoot(EntityShootBowEvent event) {
		if(event.getEntity().getKiller() instanceof Player){
			Player p = event.getEntity().getKiller();
			String hashmap = gracze.get(p.getName());
			if(!hashmap.equals("stoi")) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
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
		}

	}


	@EventHandler
	public void wezitem(PlayerPickupItemEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void regeneracjaHP(EntityRegainHealthEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (!gracze.get(p.getName()).equals("stoi")) {
				e.setCancelled(true);
			}
		}

	}

	@EventHandler(priority = EventPriority.LOW)
	public void commandblock(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			if (!event.getMessage().toLowerCase().startsWith("/zo") && !event.getMessage().toLowerCase().startsWith("/zginodrazu") && !event.getMessage().toLowerCase().startsWith("/deathnow") && !event.getMessage().toLowerCase().startsWith("/dn")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
			}
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
		pose.stop(p);
	}
}
