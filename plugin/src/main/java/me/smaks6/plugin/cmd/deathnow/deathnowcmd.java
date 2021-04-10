package me.smaks6.plugin.cmd.deathnow;

import me.smaks6.plugin.Main;
import me.smaks6.api.NokautEnum;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static me.smaks6.plugin.Main.gracze;

public class deathnowcmd implements CommandExecutor{

	public deathnowcmd(Main main) {
		Main.getInstance().getCommand("zginodrazu").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("zginodrazu")) {
			Player p = (Player) sender;
			NokautEnum hashmap = gracze.get(p);
			if(!hashmap.equals(NokautEnum.STOI)) {
				p.setHealth(0);
				gracze.replace(p, NokautEnum.STOI);
			}else {
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("deathnownot"));
			}

		}
		return false;
	}
}