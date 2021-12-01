package me.smaks6.plugin.utilities;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.Reflection.old.Npc.Npc;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnables {

    public static void revivePlayer(Player player, Player reviver){

        Boolean requireLevelToRevive = Main.getInstance().getConfig().getBoolean("RequireLevelToRevive");

        if(player.getLevel() < 5 && requireLevelToRevive){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Main.getInstance().getConfig().getString("NotEnoughLevelMessage")));
            return;
        }

        Block block = player.getLocation().getBlock();
        new BukkitRunnable() {

            int time = 0;

            @Override
            public void run() {

                if(!player.isSneaking() || !block.equals(player.getLocation().getBlock()) || PlayerUtility.isNull(reviver)){
                    this.cancel();
                }

                player.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + time + "%", 1 , 20 , 1 );
                reviver.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + time + "%", 1 , 20 , 1 );

                if(requireLevelToRevive && time % 20 == 0 && time > 0){
                    player.setLevel(player.getLevel() - 1);
                }

                if(time >= 100){
                    this.cancel();
                    player.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupdamager").replace("{player}", reviver.getName()));
                    reviver.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupplayer").replace("{player}", player.getName()));
                    PoseUtility.stop(reviver);
                    reviver.removePotionEffect(PotionEffectType.BLINDNESS);
                }

                ++time;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);
    }

    public static void nokautTimer(Player player){
        new BukkitRunnable() {
            int timeInSeconds = Main.getInstance().getConfig().getInt("NokautTime");
            int timeInMinutes = 0;
            String timeToDisplay;
            @Override
            public void run() {

                if(timeInSeconds > 60){
                    timeInMinutes = timeInSeconds / 60;
                    timeInSeconds = timeInSeconds - (timeInMinutes*60);
                }

                if(!PlayerUtility.isNull(player)) {

                    if (timeInSeconds <= 9) {
                        timeToDisplay = timeInMinutes + ":0" + timeInSeconds;
                    } else {
                        timeToDisplay = timeInMinutes + ":" + timeInSeconds;
                    }

                    player.sendTitle(ChatColor.RED + Main.getInstance().getConfig().getString("NokautTitle"), ChatColor.WHITE + timeToDisplay, 1, 20, 1);

                    if (PlayerUtility.isNull(player)) {
                        cancel();
                        return;
                    }

                    if ((timeInSeconds <= 0) && (timeInMinutes >= 1)) {
                        --timeInMinutes;
                        timeInSeconds = 60;
                    }

                    if ((timeInMinutes <= 0) && (timeInSeconds <= 0)) {
                        if (Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")) {

                            EntityDamageEvent lastDamageCause = player.getLastDamageCause();
                            if (lastDamageCause instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
                                Entity damager = damageByEntityEvent.getDamager();
                                player.damage(500, damager);
                            } else {
                                player.setHealth(0);
                            }

                            PoseUtility.stop(player);
                        } else {
                            PoseUtility.stop(player);
                        }
                        cancel();
                        return;
                    }

                    if (!PlayerUtility.isNull(player)) {
                        if (PlayerUtility.getState(player).equals(Nokaut.LAY)) {
                            --timeInSeconds;
                        }
                    } else {
                        cancel();
                        return;
                    }
                }else{
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }


    public static void npcTimer(Player knockedPlayer, Npc npc){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(knockedPlayer.isOnline()) {
                    if (PlayerUtility.isNull(knockedPlayer)) {
                        npc.deleteEntity();
                        cancel();
                        return;
                    }

                    if (PlayerUtility.getState(knockedPlayer).equals(Nokaut.LAY)) {
                        npc.teleportEntity(-0.1);
                    }

                    if (PlayerUtility.getState(knockedPlayer).equals(Nokaut.CARRY)) {
                        npc.teleportEntity(1.0);
                    }


                }else {
                    npc.deleteEntity();
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, Main.getInstance().getConfig().getInt("NpcTeleportTime"));
    }
}
