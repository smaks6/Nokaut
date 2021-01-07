package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.armagidon.poseplugin.api.PosePluginAPI;
import ru.armagidon.poseplugin.api.player.PosePluginPlayer;

import java.util.List;

import static me.smaks6.nokaut.Main.gracze;

public class ocuc implements Listener{

	@EventHandler
	public void Ocuc(EntityDamageByEntityEvent e) {

	}

	@EventHandler
	public void ocucS(PlayerToggleSneakEvent e){
		Player p = e.getPlayer();
		List<Entity> players = p.getNearbyEntities(4,6,4);;
		if(players.isEmpty()){
			return;
		}
		if(CitizensListener.isNpc(players.get(0))){
			return;
		}
		if(players.get(0) instanceof Player){
			Player plist = (Player) players.get(0);
			String hashmap = gracze.get(plist.getName());
			if(hashmap.equals("lezy")){
				ocucanie(p, plist);
			}
		}

	}


	public void ocucanie(Player p, Player ocucany){
		new BukkitRunnable() {

			int czas = 0;

			@Override
			public void run() {

				if(!p.isSneaking()){
					this.cancel();
				}

				p.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + czas + "%", 1 , 20 , 1 );

				if(czas >= 100){
					this.cancel();
					gracze.replace(ocucany.getName(), "stoi");
					p.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupdamager").replace("{player}", ocucany.getName()));
					ocucany.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupplayer").replace("{player}", p.getName()));
					PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(ocucany);
					posePluginPlayer.resetCurrentPose();
					ocucany.removePotionEffect(PotionEffectType.BLINDNESS);
				}

				++czas;
			}
		}.runTaskTimer(Main.getInstance(), 0L, 2L);
	}

}
