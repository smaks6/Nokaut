
//
// Copyright (c) 2021 smaks6
//

package me.smaks6.nokaut;

import java.util.HashMap;

import org.bstats.bukkit.Metrics;
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

		int pluginId = 9923;
		Metrics metrics = new Metrics(this, pluginId);
		
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
		new nokautcmd(this);
		getCommand("nokaut").setTabCompleter(new tabnokautcmd());

		getConfig().addDefault("NokautTimeInMin", 2);
		getConfig().addDefault("cancelmessage", "Nie możesz tego robić w czasie nokautu");
		getConfig().addDefault("helpnokautmessage", "Musisz poprosić innego gracza aby cię uderzył(ocucił) przed minięciem czasu inaczej zginiesz....");
		getConfig().addDefault("wakeupplayer", "Zostałeś ocucony przez {player}");
		getConfig().addDefault("wakeupdamager", "ocuciłeś gracza {player}");
		getConfig().addDefault("deathnownot", "Ta komenda jest dostępna tylko podczas nokautu");
		getConfig().addDefault("NokautTitle", "Nokaut");
		getConfig().addDefault("DeathOnEnd", "true");
		getConfig().addDefault("BlindnessOnNokaut", "true");
		getConfig().addDefault("WakeUpTitle", "Reanimowanie");
		getConfig().addDefault("LyingPosition", "true");
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

        new updatechecker(85152).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
            	Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "You have the latest version of the plugin");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Nokaut plugin BY smaks6");
            } else {
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You don't have the latest plugin version");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "\\          /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " \\        /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  \\      /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "   \\    /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "    \\  /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "     \\/");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Nokaut plugin BY smaks6");
            }
        });

	}
	
	public static Main getInstance() {
	    return instance;
	}
}



	
	
