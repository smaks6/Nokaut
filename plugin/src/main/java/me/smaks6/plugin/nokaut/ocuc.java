package me.smaks6.plugin.nokaut;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.pose;
import me.smaks6.plugin.service.CitizensListener;
import me.smaks6.plugin.utilities.NokautEnum;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static me.smaks6.plugin.Main.gracze;

public class ocuc implements Listener{

	@EventHandler
	public void Ocuc(EntityDamageByEntityEvent e) {

	}

	@EventHandler
	public void ocucS(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		List<Entity> players = p.getNearbyEntities(2,2,2);
		if(players.isEmpty()){
			return;
		}

		for (Entity player : players) {
			if (CitizensListener.isNpc(player)) {
				return;
			}
			if(player instanceof Player){
				Player plist = (Player) player;
				NokautEnum hashmap = gracze.get(plist);
				if(hashmap.equals(NokautEnum.LEZY)){
					ocucanie(p, plist);
					break;
				}
			}

		}

		return;

	}


	public void ocucanie(Player p, Player ocucany){
		new BukkitRunnable() {

			int czas = 0;

			final Location loc = p.getLocation();

			@Override
			public void run() {

				if(!p.isSneaking() || !loc.equals(p.getLocation())){
					this.cancel();
				}

				p.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + czas + "%", 1 , 20 , 1 );

				if(czas >= 100){
					this.cancel();
					p.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupdamager").replace("{player}", ocucany.getName()));
					ocucany.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupplayer").replace("{player}", p.getName()));
					pose.stop(ocucany);
					ocucany.removePotionEffect(PotionEffectType.BLINDNESS);
				}

				++czas;
			}
		}.runTaskTimer(Main.getInstance(), 0L, 2L);
	}

}
