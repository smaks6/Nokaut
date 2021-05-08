package me.smaks6.plugin.cmd.deathnow;

import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class deathnowcmd implements CommandExecutor{

	public deathnowcmd(Main main) {
		Main.getInstance().getCommand("zginodrazu").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("zginodrazu")) {
			Player p = (Player) sender;
			if(!PlayerUtilities.isNull(p)) {
				EntityDamageEvent lastDamageCause = p.getLastDamageCause();
				if(lastDamageCause instanceof EntityDamageByEntityEvent){
					EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
					Entity damager = damageByEntityEvent.getDamager();
					p.damage(20000, damager);
				}else {
					p.setHealth(0);
				}

				PlayerUtilities.unSet(p);
			}else {
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("deathnownot"));
			}

		}
		return false;
	}
}
