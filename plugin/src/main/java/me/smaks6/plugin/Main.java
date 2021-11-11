
//
// Copyright (c) 2021 smaks6
//

package me.smaks6.plugin;

import me.smaks6.plugin.commands.PluginCommand;
import me.smaks6.plugin.commands.DeathNowCommand;
import me.smaks6.plugin.commands.DropPlayerCommand;
import me.smaks6.plugin.commands.NokautCommand;
import me.smaks6.plugin.commands.tabcomplete.TabNokautCommand;
import me.smaks6.plugin.commands.PickUpPlayerCommand;
import me.smaks6.plugin.utilities.PoseUtility;
import me.smaks6.plugin.service.WorldGuardService;
import me.smaks6.plugin.service.UpdateCheckerService;
import me.smaks6.plugin.utilities.PlayerUtility;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Stream;


public class Main extends JavaPlugin{
	
	private static Main instance;

	private String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	
//    public static HashMap<Player, NokautEnum> gracze = new HashMap<Player, NokautEnum>();// value = stoi, lezy, nies

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
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Server version: " + version);

		int pluginId = 9923;
		Metrics metrics = new Metrics(this, pluginId);

		try {
			registerEvents();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


		//register WorldguardFlag
		if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			new WorldGuardService().registerFlag();
		}
		
		registerCommands();
		getCommand("nokaut").setTabCompleter(new TabNokautCommand());

		if(!getDescription().getAuthors().contains("smaks6")){
			getLogger().info("Please..........");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

        new UpdateCheckerService(85152).getVersion(version -> {
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

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!PlayerUtility.isNull(player)) {
				if (Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")) {

					EntityDamageEvent lastDamageCause = player.getLastDamageCause();
					if (lastDamageCause instanceof EntityDamageByEntityEvent) {
						EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
						Entity damager = damageByEntityEvent.getDamager();
						player.damage(500, damager);
						PoseUtility.stop(player);
					} else {
						player.setHealth(0);
					}

					PoseUtility.stop(player);
				} else {
					PoseUtility.stop(player);
					player.removePotionEffect(PotionEffectType.BLINDNESS);
				}
			}
		}
	}

	public static Main getInstance() {
	    return instance;
	}

	private void registerEvents() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections("me.smaks6.plugin");
		Set<Class<? extends Listener>> classSet = reflections.getSubTypesOf(Listener.class);
		for (Class<?> aClass : classSet) {
			Object o = aClass.getConstructor().newInstance();
			Bukkit.getServer().getPluginManager().registerEvents((Listener) o, this);
		}
	}

	private void registerCommands() {
		Stream.of(
				new DeathNowCommand(),
				new DropPlayerCommand(),
				new NokautCommand(),
				new PickUpPlayerCommand()
		).forEach(PluginCommand::enable);
	}


}



	
	
