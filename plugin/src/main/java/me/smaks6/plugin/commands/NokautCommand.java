package me.smaks6.plugin.commands;


import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.NokautUtility;
import me.smaks6.plugin.utilities.PoseUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NokautCommand implements CommandExecutor, PluginCommand{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 0) {

			if(args[0].equalsIgnoreCase("reload")){
				sender.sendMessage(ChatColor.GREEN + "Reloading plugin");
				Main.getInstance().reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Reloaded plugin");
			}

			else if(args[0].equals("wakeup") && args.length >= 2){
				Player playerToRevive = Bukkit.getPlayer(args[1]);
				if(playerToRevive == null){
					sender.sendMessage("There is no such player on the server");
					return false;
				}

				PoseUtility.stop(playerToRevive);
			}

			else if(args[0].equals("nokaut") && args.length >= 2){
				Player playerToNokaut = Bukkit.getPlayer(args[1]);
				if(playerToNokaut == null){
					sender.sendMessage("There is no such player on the server");
					return false;
				}

				NokautUtility.doNokaut(playerToNokaut, 1000);
			}
			else{
				sender.sendMessage(ChatColor.RED + "You must provide an argument");
				return false;
			}
		}else {
			sender.sendMessage(ChatColor.RED + "You must provide an argument");
		}

		return false;
	}

	@Override
	public void enable() {
		Main.getInstance().getCommand("nokaut").setExecutor(this);
	}
}
