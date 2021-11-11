package me.smaks6.plugin.commands;

import me.smaks6.plugin.utilities.NokautUtility;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DeathNowCommand implements CommandExecutor, PluginCommand {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			Player p = (Player) sender;
			if(!PlayerUtility.isNull(p)) {
				Entity lastDamager = NokautUtility.getLastDamager(p);

				if(lastDamager == null){
					p.setHealth(0);
				}else{
					p.damage(300, lastDamager);
				}

				PlayerUtility.unSet(p);
			}else {
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("deathnownot"));
			}
		return false;
	}

	@Override
	public void enable() {
		Main.getInstance().getCommand("zginodrazu").setExecutor(this);
	}
}
