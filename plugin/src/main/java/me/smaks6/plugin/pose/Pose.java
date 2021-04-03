package me.smaks6.plugin.pose;

import me.smaks6.api.NokautEnum;
import me.smaks6.plugin.Main;
import me.smaks6.v1_16_R3.CreateNPC;
import me.smaks6.v1_16_R3.OtherMetchod;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static me.smaks6.plugin.Main.gracze;

public class Pose{


    //start pose animation
    public static void start(Player p) {
        gracze.replace(p, NokautEnum.LEZY);
        p.setWalkSpeed(0);
        p.setFlySpeed(0);

        p.setInvisible(true);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            createNPC(p, onlinePlayer);
        }

        hidePlayers(p);

    }

    //stop pose animation
    public static void stop(Player p){
        gracze.replace(p, NokautEnum.STOI);

        p.setDisplayName(p.getName());

        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.1F);

        p.setInvisible(false);
        p.setGameMode(GameMode.SURVIVAL);

        showPlayers(p);
    }

    public static void createNPC(Player znokautowany, Player see){
        new CreateNPC(znokautowany, see, gracze);
    }

    //change game mode
    public static void changegamemode(Player p, Player reviever, boolean nies){
        //nies:
        //true - na plecach niech idzie
        //false - niech już spada z pleców
        OtherMetchod.changeGameMode(p, reviever, nies);
    }


    public static void hidePlayers(Player player) {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.hidePlayer(Main.getInstance(), player);
        }
    }

    public static void showPlayers(Player player){
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.showPlayer(Main.getInstance(), player);
        }
    }

}
