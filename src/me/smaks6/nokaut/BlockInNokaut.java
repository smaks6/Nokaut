package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import me.smaks6.nokaut.Main;

import ru.armagidon.poseplugin.api.events.StopPosingEvent;

public class BlockInNokaut extends JavaPlugin implements Listener{
	
	@EventHandler
	public void kladz(BlockPlaceEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(hashmap != "stoi") {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler
	public void niszcz(BlockBreakEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(hashmap != "stoi") {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}
		
	}
	

	
	@EventHandler
	public void niewstawaj(StopPosingEvent event) {
		Player p = event.getPlayer().getHandle();
		String hashmap = gracze.get(p.getName());
		if(hashmap == "nies") {
			event.setCancelled(false);
			return;
		}
		if(hashmap != "stoi") {
			event.setCancelled(true);
		}else {
			event.setCancelled(false);
		}
		

		
	}
	
	@EventHandler
	public void chestitd(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(hashmap != "stoi") {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + getConfig().getString("cancelmessage"));
		}else {
			event.setCancelled(false);
		}
		
	}
}
