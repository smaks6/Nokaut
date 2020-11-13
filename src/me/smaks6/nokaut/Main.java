package me.smaks6.nokaut;



import java.util.HashMap;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import ru.armagidon.poseplugin.api.PosePluginAPI;
import ru.armagidon.poseplugin.api.player.PosePluginPlayer;
import ru.armagidon.poseplugin.api.poses.EnumPose;
import ru.armagidon.poseplugin.api.poses.IPluginPose;
import ru.armagidon.poseplugin.api.poses.PoseBuilder;
import ru.armagidon.poseplugin.api.poses.options.EnumPoseOption;
import ru.armagidon.poseplugin.api.utils.npc.HandType;


public class Main extends JavaPlugin implements Listener{
	
    public static HashMap<String, String> gracze = new HashMap<String, String>();
    
	public void onEnable() {
		
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|\\     |  |  /");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| \\    |  | /");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|  \\   |  |/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|   \\  |  |\\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|    \\ |  | \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|     \\|  |  \\");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling the plugin nokaut BY smaks6");
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		getConfig().addDefault("NokautTimeInMin", 2);
		getConfig().addDefault("cancelmessage", "Nie mo¿esz tego robiæ w czasie nokautu");
		getConfig().addDefault("helpnokautmessage", "Musisz poprosiæ innego gracza aby ciê uderzy³(ocuci³) przed miniêciem czasu inaczej zginiesz....");
		getConfig().addDefault("wakeupplayer", "Zosta³eœ ocucony przez {player}");
		getConfig().addDefault("wakeupdamager", "ocuci³eœ gracza {player}");
		getConfig().addDefault("deathnownot", "Ta komenda jest dostêpna tylko podczas nokautu");
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("zginodrazu")) {
			Player p = (Player) sender;
			String hashmap = gracze.get(p.getName());
			if(hashmap != "stoi") {
				p.setHealth(0);
				gracze.replace(p.getName(), "stoi");
			}else {
				p.sendMessage(ChatColor.RED + getConfig().getString("deathnownot"));
			}

		}
		return false;
	}
	
	@EventHandler
    public void death(EntityDamageEvent event) throws InterruptedException{
        if (event.getEntity() instanceof Player){
            Player p = (Player) event.getEntity();
            int hp = (int) p.getHealth();
            int dm = (int) event.getDamage();
            String hashmap = gracze.get(p.getName());
        	if(hp <= dm) {
                if(hashmap == "stoi") {
                	gracze.replace(p.getName(), "chwila");
                	event.setCancelled(true);
                    PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(p);
                    IPluginPose pose = PoseBuilder.builder(EnumPose.LYING).option(EnumPoseOption.HANDTYPE, HandType.LEFT).build(p);
                    posePluginPlayer.changePose(pose);
    				if(p.getHealth() <= 10.0) {
    					p.setHealth(10.0);
    				}
    				p.sendMessage(ChatColor.RED + getConfig().getString("helpnokautmessage"));
    				odliczanie(p);
                }else if(hashmap != "stoi") {
                	event.setCancelled(false);
                }else {
                	p.kickPlayer("[nokaut] An unexpected error has occurred in the program");
                }
				
        	}
        }
           
    }
	
	
	
	@EventHandler
	public void ocuc(EntityDamageByEntityEvent e) {
		Entity entityhp = e.getEntity();
		Entity entityd = e.getDamager();
		if(entityhp instanceof Player && entityd instanceof Player){
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			String hashmap = gracze.get(p.getName());
			String hashmapdamager = gracze.get(d.getName());
			if(hashmap == "chwila") {
				e.setCancelled(true);
				return;
			}
			if(hashmap == "nies") {
				gracze.replace(p.getName(), "lezy");
				Location dloc = d.getLocation();
				p.teleport(dloc);
				try {
					Thread.sleep(500);//przeczekanie 0,5 sekundy
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(p);
                IPluginPose pose = PoseBuilder.builder(EnumPose.LYING).option(EnumPoseOption.HANDTYPE, HandType.LEFT).build(p);
                posePluginPlayer.changePose(pose);
                e.setCancelled(true);
				try {
					Thread.sleep(500);//przeczekanie 0,5 sekundy
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                p.leaveVehicle();
                return;
			}
			
			if(hashmapdamager != "stoi") {
				e.setCancelled(true);
				d.sendMessage(ChatColor.RED + getConfig().getString("cancelmessage"));
				return;
			}
			if(hashmap == "lezy"){
				gracze.replace(p.getName(), "stoi");
				p.sendMessage(ChatColor.DARK_GREEN + getConfig().getString("wakeupplayer").replace("{player}", d.getName()));
				d.sendMessage(ChatColor.DARK_GREEN + getConfig().getString("wakeupdamager").replace("{player}", p.getName()));
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PosePluginPlayer posePluginPlayer = PosePluginAPI.getAPI().getPlayerMap().getPosePluginPlayer(p);
                posePluginPlayer.resetCurrentPose();
                d.removePassenger(p);
			}
			
			
		}else if(entityhp instanceof Player) {
			Player p = (Player) e.getEntity();
			String hashmap = gracze.get(p.getName());
			if(hashmap == "lezy") {
				e.setCancelled(true);
			}else {
				e.setCancelled(false);
			}

		}else if(entityd instanceof Player){
			Player p = (Player) e.getDamager();
			String hashmap = gracze.get(p.getName());
			if(hashmap != "stoi") {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + getConfig().getString("cancelmessage"));
			}
		}else {
			e.setCancelled(false);
		}
		
	}
	
	@EventHandler
	public void podniescgracza(PlayerInteractEntityEvent e) {//Event który jest odpowiedzialny za mo¿liwoœæ wziêcia gracza na ³ep i pójœcia sobie gdzieœ z nim :)
		Player p = e.getPlayer();//pobieranie gracza od eventu
		String hashmap = gracze.get(p.getName());//pobieranie danych o graczu z hashmapy
		if(hashmap != "stoi") {//if sprawdzaj¹cy czy gracz stoi je¿eli tak to kod idzie dalej je¿eli nie to koniec kodu
			return;
		}
		Entity rightclick = e.getRightClicked();//pobieranie entity na któr¹ klikn¹ gracz
		if(rightclick instanceof Player){//sprawdzanie czy klikniête entity to gracz
			Player playeron = (Player) e.getRightClicked();//pobieranie gracza który zosta³ klikniêty
			String hashmapon = gracze.get(playeron.getName());//pobieranie hashmapy dla gracza klikniêtego
			if(hashmapon == "stoi") {//if sprawdzaj¹cy czy gracz klikniêty stoi je¿eli tak to koniec kodu
				return;
			}
			gracze.replace(playeron.getName(), "nies");//zmiana hashmapy ¿eby móg³ podnieœæ gracza
			p.addPassenger(playeron);//dodawanie gracza klikniêtego na ³ep gracza który klikn¹³ go 
			try {
				Thread.sleep(50);//przeczekanie 0,05 sekundy
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			p.addPassenger(playeron);//dodawanie gracza klikniêtego na ³ep gracza który klikn¹³ go
			siedzisz(p, playeron);//w³¹czanie metody ¿eby gracz nie móg³ siadaæ z gracza
		}
	}
	
	
	public void odliczanie(Player p){
		new BukkitRunnable() {
			
    		int czass = 0;
    		int czasm = getConfig().getInt("NokautTimeInMin");
    		String razem;
    		
			@Override
	        public void run() {
				
	    		String hashmap = gracze.get(p.getName());
				if(hashmap == "stoi") {
					this.cancel();
				}
				
				if(hashmap == "chwila" && czass == 55) {
					gracze.replace(p.getName(), "lezy");
				}

				
    			if((czass <= 0) && (czasm >= 1)) {
    				--czasm;
    				czass = 60;
    			}
    			
    			if((czasm <= 0) && (czass <= 0)) {
    				gracze.replace(p.getName(), "stoi");
    				p.setHealth(0);
    				this.cancel();
    			}
    			
    			if(czass <= 9) {
        			razem = czasm + ":0" + czass;
    			}else {
        			razem = czasm + ":" + czass;
    			}

    			
    			p.sendTitle(ChatColor.RED + "Nokaut",ChatColor.RED + razem, 1 , 20 , 1 );
    			
    			--czass;
	        }
	    }.runTaskTimer(this, 0L, 20L);
	}
	
	
	public void siedzisz(Player p, Player d){
		new BukkitRunnable() {
			
			int czas = 50;
			
			@Override
	        public void run() {
				
				String hashmap = gracze.get(d.getName());
				
				if(d.isInsideVehicle() == false || d.getVehicle() instanceof Player){
					p.addPassenger(d);
					
				}
				
				if(hashmap != "nies" && czas == 0){
					this.cancel();
				}
				
				if(czas != 0) {
					--czas;
				}
				
				
	        }
	    }.runTaskTimer(this, 0L, 5L);
	}

	
	@EventHandler
	public void wchodzi(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		gracze.put(p.getName(), "stoi");
		
	}
	
	@EventHandler
	public void wychodzi(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		String hashmap = gracze.get(p.getName());
		if(hashmap != "stoi") {
			p.setHealth(0);
		}
		gracze.remove(p.getName());
		
	}
	
	@EventHandler
	public void smierc(PlayerDeathEvent event) {
		Player p =(Player) event.getEntity();
		gracze.replace(p.getName(), "stoi");
		
	}
	

}



	
	
