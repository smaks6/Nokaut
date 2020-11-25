package me.smaks6.nokaut;

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
		Entity rightclick = e.getRightClicked();
		if(rightclick instanceof Player){
			Player playeron = (Player) e.getRightClicked();
			String hashmapon = gracze.get(playeron.getName());
			if(hashmapon.equals("stoi")) {
				return;
			}
			if(hashmapon.equals("nies")) {
				return;
			}
			gracze.replace(playeron.getName(), "nies");
			p.addPassenger(playeron);
			p.addPassenger(playeron);
			siedzisz(p, playeron);
		}
	}
	
	public void siedzisz(Player p, Player d){
		new BukkitRunnable() {
			
			int czas = 50;
			
			@Override
	        public void run() {
				
				String hashmap = gracze.get(d.getName());
				
				if(!d.isInsideVehicle() || d.getVehicle() instanceof Player){
					p.addPassenger(d);
					
				}
				
				if(!hashmap.equals("nies") && czas == 0){
					this.cancel();
				}
				
				if(czas != 0) {
					--czas;
				}
				
				
	        }
	    }.runTaskTimer(Main.getInstance(), 0L, 5L);
	}
}
