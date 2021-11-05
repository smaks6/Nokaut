package me.smaks6.plugin.cmd.deathnow;

import me.smaks6.plugin.utilities.NokautUtilities;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathnowCmd implements CommandExecutor{

	public DeathnowCmd(Main main) {
		Main.getInstance().getCommand("zginodrazu").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			Player p = (Player) sender;
			if(!PlayerUtilities.isNull(p)) {
				Entity lastDamager = NokautUtilities.getLastDamager(p);

				if(lastDamager == null){
					p.setHealth(0);
				}else{
					p.damage(300, lastDamager);
				}

				PlayerUtilities.unSet(p);
			}else {
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("deathnownot"));
			}
		return false;
	}
}
