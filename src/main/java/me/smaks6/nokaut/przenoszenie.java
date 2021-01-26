package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static me.smaks6.nokaut.Main.gracze;

public class przenoszenie implements Listener{

	@EventHandler
	public void podniescgracza(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(!hashmap.equals("stoi")) {
			return;
		}
		if(CitizensListener.isNpc(e.getRightClicked())){
			return;
		}
		Entity rightclick = e.getRightClicked();
		if(rightclick instanceof Player){
			Player playeron = (Player) e.getRightClicked();
			String hashmapon = gracze.get(playeron.getName());
			if(hashmapon.equals("stoi")) {
				return;
			}
			else if(hashmapon.equals("nies")) {
				return;
			}
			else if(hashmapon.equals("lezy")){
				gracze.replace(playeron.getName(), "nies");
				siedzisz(p, playeron);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void Ocuc(EntityDamageByEntityEvent e) {
		Entity entityhp = e.getEntity();
		Entity entityd = e.getDamager();
		if(CitizensListener.isNpc(e.getEntity())){
			return;
		}
		if(entityhp instanceof Player && entityd instanceof Player){
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			String hashmap = gracze.get(p.getName());
			String hashmapdamager = gracze.get(d.getName());
			if(hashmap.equals("nies")) {
				gracze.replace(p.getName(), "lezy");
				pose.usunblock(p);
				e.setCancelled(true);
				return;
			}

			if(!hashmapdamager.equals("stoi")) {
				e.setCancelled(true);
				d.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
				return;
			}
			if(hashmap.equals("lezy")){
				e.setCancelled(true);
			}


		}else if(entityhp instanceof Player) {
			Player p = (Player) e.getEntity();
			String hashmap = gracze.get(p.getName());
			e.setCancelled(hashmap.equals("lezy"));

		}else if(entityd instanceof Player){
			Player p = (Player) e.getDamager();
			String hashmap = gracze.get(p.getName());
			if(!hashmap.equals("stoi")) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
			}
		}else {
			e.setCancelled(false);
		}

	}
	
	public void siedzisz(Player p, Player d){
		new BukkitRunnable() {
			
			@Override
	        public void run() {
				
				String hashmap = gracze.get(d.getName());

				d.teleport(p.getLocation().add(0,2,0));

				if(!d.isOnline() || !p.isOnline()){
					this.cancel();
					return;
				}
				
				if(!hashmap.equals("nies")){
					this.cancel();
					return;
				}

				pose.usunblock(d);

				
				
	        }
	    }.runTaskTimer(Main.getInstance(), 0L, 1L);
	}
}
