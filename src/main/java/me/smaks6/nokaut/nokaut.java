package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.smaks6.nokaut.Main.gracze;
import static me.smaks6.nokaut.Main.attacker;

public class nokaut implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void getattacker(EntityDamageByEntityEvent e) {
		if (checkNokaut(e.getEntity(), e.getFinalDamage(), e.getDamager())) {
			e.setCancelled(true);
		}
		Main.getInstance().getServer().broadcastMessage("byentity");

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void death(EntityDamageEvent event) {
		if (checkNokaut(event.getEntity(), event.getFinalDamage(), null)) {
			event.setCancelled(true);
		}

		Main.getInstance().getServer().broadcastMessage("entity");

	}


	public boolean checkNokaut(Entity e, double dmm, Entity d) {
		if (CitizensListener.isNpc(e)) {
			return false;
		}
		if (e instanceof Player) {
			Player p = (Player) e;
			int hp = (int) p.getHealth();
			int dm = (int) dmm;
			String hashmap = gracze.get(p.getName());
			if (!hashmap.equals("stoi")) {
				return false;
			}
			if (hp <= dm) {
				if(d instanceof Player){
					attacker.put(p,(Player) d);
					p.sendMessage("dodano!");
				}
				gracze.replace(p.getName(), "lezy");
				p.setFireTicks(0);
				p.setHealth(2);

				pose.start(p);

				if (Main.getInstance().getConfig().getString("BlindnessOnNokaut").equals("true")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 100));
				}

				if (p.getHealth() <= 10.0) {
					p.setHealth(10.0);
				}
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("helpnokautmessage"));
				odliczanie(p);
				return true;
			}

		}
		return false;
	}

	public void odliczanie(Player p){
		new BukkitRunnable() {
			Player pattacker;
    		int czass = 0;
    		int czasm = Main.getInstance().getConfig().getInt("NokautTimeInMin");
    		String razem;


			@Override
	        public void run() {

				if(czass <= 9) {
					razem = czasm + ":0" + czass;
				}else {
					razem = czasm + ":" + czass;
				}

				p.sendTitle(ChatColor.RED + Main.getInstance().getConfig().getString("NokautTitle"),ChatColor.WHITE + razem, 1 , 20 , 1 );

	    		String hashmap = gracze.get(p.getName());
				if(!p.isOnline()){
					this.cancel();
				}
				if(hashmap.equals("stoi")) {
					this.cancel();
				}

    			if((czass <= 0) && (czasm >= 1)) {
    				--czasm;
    				czass = 60;
    			}
    			
    			if((czasm <= 0) && (czass <= 0)) {
					gracze.replace(p.getName(), "stoi");
    				if(Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")){
    					if(attacker.containsKey(p)){
							attacker.get(p).damage(200, p);
							attacker.remove(p);
						}
    					else {
							p.setHealth(0);
						}
					}
					else{

						pose.stop(p);
						p.removePotionEffect(PotionEffectType.BLINDNESS);
					}
    				this.cancel();
    			}
    			
    			--czass;
	        }
	    }.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
	
}
