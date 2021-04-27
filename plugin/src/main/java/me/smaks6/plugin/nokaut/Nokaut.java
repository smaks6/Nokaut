package me.smaks6.plugin.nokaut;

import me.smaks6.api.NokautEnum;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import me.smaks6.plugin.service.CitizensListener;
import me.smaks6.plugin.service.WorldGuardFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.smaks6.plugin.Main.gracze;

public class Nokaut implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void death(EntityDamageEvent event) {

		if(event.getEntity() instanceof Player) {

			if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
				return;
			}

			Player player = (Player) event.getEntity();
			if(player.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) ||
					player.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING))return;

			if(Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
				if (WorldGuardFlag.isFlag(player)) return;
			}

			if (checkNokaut(event.getEntity(), event.getFinalDamage())) {
				event.setCancelled(true);
			}
		}
	}

	public boolean checkNokaut(Entity e, double dmm) {
		if (CitizensListener.isNpc(e)) {
			return false;
		}
		if (e instanceof Player) {
			Player p = (Player) e;
			int hp = (int) p.getHealth();
			int dm = (int) dmm;
			NokautEnum hashmap = gracze.get(p);
			if (!hashmap.equals(NokautEnum.STOI)) {
				return false;
			}
			if (hp <= dm) {

				if(!p.getPassengers().isEmpty()){
					Player znokautowany = (Player) p.getPassengers().get(0);
					gracze.replace(znokautowany, NokautEnum.LEZY);
					p.getPassengers().clear();
					Pose.changegamemode(znokautowany, p, false);
				}

				gracze.replace(p, NokautEnum.LEZY);
				p.setFireTicks(0);
				p.setHealth(2);

				Pose.start(p);
				
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

	    		NokautEnum hashmap = gracze.get(p);

				if(hashmap == null){
					this.cancel();
				}

				if(hashmap.equals(NokautEnum.STOI)) {
					this.cancel();
				}

    			if((czass <= 0) && (czasm >= 1)) {
    				--czasm;
    				czass = 60;
    			}
    			
    			if((czasm <= 0) && (czass <= 0)) {
					gracze.replace(p, NokautEnum.STOI);
    				if(Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")){
    					p.setHealth(0);
						System.out.println("koniec nokaut");
    					Pose.stop(p);
					}
					else{
						System.out.println("koniec nokaut");
						Pose.stop(p);
						p.removePotionEffect(PotionEffectType.BLINDNESS);
					}
    				this.cancel();
    			}

    			if(hashmap.equals(NokautEnum.LEZY)){
					--czass;
				}
	        }
	    }.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
	
}
