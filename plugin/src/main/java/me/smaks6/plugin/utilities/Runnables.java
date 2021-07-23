package me.smaks6.plugin.utilities;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import me.smaks6.plugin.utilities.Reflection.Old.Npc.Npc;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnables {

    public static void revievePlayer(Player p, Player ocucany){

        Block block = p.getLocation().getBlock();
        new BukkitRunnable() {

            int czas = 0;

            @Override
            public void run() {

                if(!p.isSneaking() || !block.equals(p.getLocation().getBlock())){
                    this.cancel();
                }

                p.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + czas + "%", 1 , 20 , 1 );
                ocucany.sendTitle(ChatColor.GREEN + Main.getInstance().getConfig().getString("WakeUpTitle"),ChatColor.GREEN + "" + czas + "%", 1 , 20 , 1 );

                if(czas >= 100){
                    this.cancel();
                    p.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupdamager").replace("{player}", ocucany.getName()));
                    ocucany.sendMessage(ChatColor.DARK_GREEN + Main.getInstance().getConfig().getString("wakeupplayer").replace("{player}", p.getName()));
                    Pose.stop(ocucany);
                    ocucany.removePotionEffect(PotionEffectType.BLINDNESS);
                }

                ++czas;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);
    }

    public static void nokautTimer(Player p){
        new BukkitRunnable() {
            int czass = 0;
            int czasm = Main.getInstance().getConfig().getInt("NokautTimeInMin");
            String razem;
            @Override
            public void run() {

                if(!PlayerUtilities.isNull(p)) {

                    if (czass <= 9) {
                        razem = czasm + ":0" + czass;
                    } else {
                        razem = czasm + ":" + czass;
                    }

                    p.sendTitle(ChatColor.RED + Main.getInstance().getConfig().getString("NokautTitle"), ChatColor.WHITE + razem, 1, 20, 1);

                    if (PlayerUtilities.isNull(p)) {
                        cancel();
                        return;
                    }

                    if ((czass <= 0) && (czasm >= 1)) {
                        --czasm;
                        czass = 60;
                    }

                    if ((czasm <= 0) && (czass <= 0)) {
                        if (Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")) {

                            EntityDamageEvent lastDamageCause = p.getLastDamageCause();
                            if (lastDamageCause instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
                                Entity damager = damageByEntityEvent.getDamager();
                                p.damage(500, damager);
                            } else {
                                p.setHealth(0);
                            }

                            Pose.stop(p);
                        } else {
                            Pose.stop(p);
                            p.removePotionEffect(PotionEffectType.BLINDNESS);
                        }
                        cancel();
                        return;
                    }

                    if (!PlayerUtilities.isNull(p)) {
                        if (PlayerUtilities.getEnum(p).equals(Nokaut.LAY)) {
                            --czass;
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
                    if (PlayerUtilities.isNull(knockedPlayer)) {
                        npc.delateEntity();
                        cancel();
                        return;
                    }

                    if (PlayerUtilities.getEnum(knockedPlayer).equals(Nokaut.LAY)) {
                        npc.teleportEntity(-0.1);
                    }

                    if (PlayerUtilities.getEnum(knockedPlayer).equals(Nokaut.CARRY)) {
                        npc.teleportEntity(1.0);
                    }


                }else {
                    npc.delateEntity();
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, Main.getInstance().getConfig().getInt("NpcTeleportTime"));
    }
}
