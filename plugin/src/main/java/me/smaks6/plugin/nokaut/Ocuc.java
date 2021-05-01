package me.smaks6.plugin.nokaut;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import me.smaks6.plugin.service.CitizensListener;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
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

public class Ocuc implements Listener{

	@EventHandler
	public void Ocuc(EntityDamageByEntityEvent e) {

	}

	@EventHandler
	public void ocucS(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		if(!gracze.get(p).equals(NokautEnum.STOI)){
			e.setCancelled(true);
			return;
		}
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

		Block block = p.getLocation().getBlock();
		new BukkitRunnable() {

			int czas = 0;

			@Override
			public void run() {

				if(!p.isSneaking() || !block.equals(p.getLocation().getBlock())){
					this.cancel();
				}

				p.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + czas + "%", 1 , 20 , 1 );
				ocucany.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + czas + "%", 1 , 20 , 1 );

				if(czas >= 100){
					this.cancel();
					p.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupdamager").replace("{player}", ocucany.getName()));
					ocucany.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupplayer").replace("{player}", p.getName()));
					Pose.stop(ocucany);
					ocucany.removePotionEffect(PotionEffectType.BLINDNESS);
				}

				++czas;
			}
		}.runTaskTimer(Main.getInstance(), 0L, 2L);
	}

}
