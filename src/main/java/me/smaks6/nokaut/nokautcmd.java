package me.smaks6.nokaut;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import static me.smaks6.nokaut.Main.gracze;

public class nokautcmd implements CommandExecutor{

	public nokautcmd(Main main) {
		Main.getInstance().getCommand("nokaut").setExecutor(this);
	}

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
}
