package me.smaks6.nokaut.nokaut;

import me.smaks6.nokaut.Main;
import me.smaks6.nokaut.pose.pose;
import me.smaks6.nokaut.service.CitizensListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
