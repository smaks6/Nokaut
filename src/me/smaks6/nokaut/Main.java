package me.smaks6.nokaut;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


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
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(BlockInNokaut, this);
		Bukkit.getServer().getPluginManager().registerEvents(nokaut, this);
		Bukkit.getServer().getPluginManager().registerEvents(ocuc, this);
		
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
			if(hashmapon == "nies") {//if sprawdza czy gracz klikniêty już jest przenoszony
				return;
			}
			gracze.replace(playeron.getName(), "nies");//zmiana hashmapy ¿eby móg³ podnieœæ gracza
			p.addPassenger(playeron);//dodawanie gracza klikniêtego na ³ep gracza który klikn¹³ go 
			p.addPassenger(playeron);//dodawanie gracza klikniêtego na ³ep gracza który klikn¹³ go
			siedzisz(p, playeron);//w³¹czanie metody ¿eby gracz nie móg³ siadaæ z gracza
		}
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
	

}



	
	
