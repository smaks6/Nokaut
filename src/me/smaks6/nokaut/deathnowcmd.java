package me.smaks6.nokaut;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static me.smaks6.nokaut.Main.gracze;

public class deathnowcmd implements CommandExecutor{

	public deathnowcmd(Main main) {
		Main.getInstance().getCommand("zginodrazu").setExecutor(this);
		return;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("zginodrazu")) {
			Player p = (Player) sender;
			String hashmap = gracze.get(p.getName());
			if(hashmap != "stoi") {
				p.setHealth(0);
				gracze.replace(p.getName(), "stoi");
			}else {
				p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("deathnownot"));
			}

		}
		return false;
	}
}
