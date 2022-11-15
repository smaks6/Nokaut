package me.smaks6.plugin.utilities;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.objects.ArmorStandNokaut;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.Enum.NokautError;
import me.smaks6.plugin.utilities.Reflection.New.GameMode.ChangeGameModeNew;
import me.smaks6.plugin.utilities.Reflection.New.Npc.NpcNew;
import me.smaks6.plugin.utilities.Reflection.old.GameMode.ChangeGameMode;
import me.smaks6.plugin.utilities.Reflection.old.Npc.Npc;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class PoseUtility {
    //start pose animation
    public static void start(Player p) {
        PlayerUtility.setState(p, Nokaut.LAY);
        if(Main.getInstance().getConfig().getBoolean("MoveWhileKnockout")){
            p.setWalkSpeed(0.02F);
            p.setFlySpeed(0.02F);
        }else {
            p.setWalkSpeed(0);
            p.setFlySpeed(0);
        }
        p.setCollidable(false);
        p.setSwimming(false);
        p.setGliding(false);
        p.setFlying(false);
        p.setSneaking(false);
        p.setSprinting(false);
        p.removePotionEffect(PotionEffectType.POISON);

        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 100));
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 180));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            createNPC(p, onlinePlayer);
        }
        hidePlayer(p);

        //do commands

        Entity damager = NokautUtility.getLastDamager(p);
        List<String> commandsOnNokaut = Main.getInstance().getConfig().getStringList("commandsOnNokaut");
        for (String command : commandsOnNokaut) {
            command = command.replaceAll("\"KnockedPlayer\"", p.getName());
            command = command.replaceAll("\"Damager\"", (damager == null ? "" : damager.getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        ArmorStandNokaut armorStandNokaut = new ArmorStandNokaut(p);
        armorStandNokaut.teleportArmorStands();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team noCollisionTeam = scoreboard.getTeam(p.getName());
        if(noCollisionTeam == null){
            noCollisionTeam = scoreboard.registerNewTeam(p.getName());
        }
        noCollisionTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        noCollisionTeam.addEntry(p.getName());
    }

    //stop pose animation
    public static void stop(Player p){
        PlayerUtility.unSet(p);
        p.setCollidable(true);

        p.setDisplayName(p.getName());

        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.BLINDNESS);
        p.removePotionEffect(PotionEffectType.JUMP);

        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.1F);

        p.setGameMode(GameMode.SURVIVAL);

        showPlayer(p);

        // do commands
        Entity damager = NokautUtility.getLastDamager(p);
        List<String> commandsAfterNokaut = Main.getInstance().getConfig().getStringList("commandsAfterNokaut");
        for (String command : commandsAfterNokaut) {
            command = command.replaceAll("\"KnockedPlayer\"", p.getName());
            command = command.replaceAll("\"Damager\"", (damager == null ? "" : damager.getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        Team team = p.getScoreboard().getTeam(p.getName());
        if(team != null) {
            team.unregister();
        }
    }

    public static void createNPC(Player knockedPlayer, Player see){
        try {
            if (Reflection.isNewPackeges()) new NpcNew(knockedPlayer, see);
            else new Npc(knockedPlayer, see);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //change game mode
    public static void changeGameMode(Player p, Player reviver, boolean nies){
        try {
            if(Reflection.isNewPackeges()) ChangeGameModeNew.changeGameMode(p, reviver, nies);
            else ChangeGameMode.changeGameMode(p, reviver, nies);
        }catch (Exception e){
            ChatUtility.sendErrorMessage(NokautError.GAMEMODE_ERROR, e.getMessage());
            e.printStackTrace();
        }


    }


    public static void hidePlayer(Player player) {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.hidePlayer(Main.getInstance(), player);
        }
    }

    public static void showPlayer(Player player){
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.showPlayer(Main.getInstance(), player);
        }
    }

}
