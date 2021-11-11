package me.smaks6.plugin.commands;


import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NokautCommand implements CommandExecutor, PluginCommand{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nokaut")) {
			Player p = (Player) sender;
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("reload")){
					p.sendMessage(ChatColor.GREEN + "Reloading plugin");
					Main.getInstance().reloadConfig();
					p.sendMessage(ChatColor.GREEN + "Reloaded plugin");
				}else{
					p.sendMessage(ChatColor.RED + "You must provide an argument");
					return false;
				}
			}else {
				p.sendMessage(ChatColor.RED + "You must provide an argument");
			}
			
		}
		return false;
	}

	@Override
	public void enable() {
		Main.getInstance().getCommand("nokaut").setExecutor(this);
	}
}
