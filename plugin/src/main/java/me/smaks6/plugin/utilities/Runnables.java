package me.smaks6.plugin.utilities;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.smaks6.plugin.Main.gracze;

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

                if(czass <= 9) {
                    razem = czasm + ":0" + czass;
                }else {
                    razem = czasm + ":" + czass;
                }

                p.sendTitle(ChatColor.RED + Main.getInstance().getConfig().getString("NokautTitle"),ChatColor.WHITE + razem, 1 , 20 , 1 );

                NokautEnum hashmap = gracze.get(p);

                if(hashmap == null){
                    this.cancel();
                }

                if(hashmap.equals(NokautEnum.STOI)) {
                    this.cancel();
                }

                if((czass <= 0) && (czasm >= 1)) {
                    --czasm;
                    czass = 60;
                }

                if((czasm <= 0) && (czass <= 0)) {
                    if(Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")){

                        EntityDamageEvent lastDamageCause = p.getLastDamageCause();
                        if(lastDamageCause instanceof EntityDamageByEntityEvent){
                            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
                            Entity damager = damageByEntityEvent.getDamager();
                            p.damage(20000, damager);
                        }else {
                            p.setHealth(0);
                        }

                        System.out.println("koniec nokaut");
                        gracze.replace(p, NokautEnum.STOI);
                        Pose.stop(p);
                    }
                    else{
                        System.out.println("koniec nokaut");
                        gracze.replace(p, NokautEnum.STOI);
                        Pose.stop(p);
                        p.removePotionEffect(PotionEffectType.BLINDNESS);
                    }
                    this.cancel();
                }

                if(hashmap.equals(NokautEnum.LEZY)){
                    --czass;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }
}
