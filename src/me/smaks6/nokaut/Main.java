package me.smaks6.nokaut;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	
    public static HashMap<String, String> gracze = new HashMap<String, String>();
    
	public void onEnable() {
		
		instance = this;
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|\\     |  |  /");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| \\    |  | /");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|  \\   |  |/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|   \\  |  |\\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|    \\ |  | \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|     \\|  |  \\");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling the plugin nokaut BY smaks6");
		
		BlockInNokaut BlockInNokaut = new BlockInNokaut();
		nokaut nokaut = new nokaut();
		ocuc ocuc = new ocuc();
		przenoszenie przenoszenie = new przenoszenie();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(BlockInNokaut, this);
		Bukkit.getServer().getPluginManager().registerEvents(nokaut, this);
		Bukkit.getServer().getPluginManager().registerEvents(ocuc, this);
		Bukkit.getServer().getPluginManager().registerEvents(przenoszenie, this);
		
		new deathnowcmd(this);
		

		getConfig().addDefault("NokautTimeInMin", 2);
		getConfig().addDefault("cancelmessage", "Nie mo¿esz tego robiæ w czasie nokautu");
		getConfig().addDefault("helpnokautmessage", "Musisz poprosiæ innego gracza aby ciê uderzy³(ocuci³) przed miniêciem czasu inaczej zginiesz....");
		getConfig().addDefault("wakeupplayer", "Zosta³eœ ocucony przez {player}");
		getConfig().addDefault("wakeupdamager", "ocuci³eœ gracza {player}");
		getConfig().addDefault("deathnownot", "Ta komenda jest dostêpna tylko podczas nokautu");
		getConfig().addDefault("NokautTitle", "Nokaut");
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

	}
	
	public static Main getInstance() {
	    return instance;
	}
}



	
	
