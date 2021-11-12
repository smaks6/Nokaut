package me.smaks6.plugin.utilities;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.service.CitizensService;
import me.smaks6.plugin.service.WorldGuardService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class NokautUtility {

    public static boolean doNokaut(Entity e, double dmm) {
        if (CitizensService.isNpc(e)) {
            return false;
        }
        if (e instanceof Player) {
            Player p = (Player) e;

            if(p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) ||
                    p.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING))return false;

            if(Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
                if (WorldGuardService.isFlag(p)) return false;
            }

            int hp = (int) p.getHealth();
            int dm = (int) dmm;

            if (!PlayerUtility.isNull(p)) {
                return false;
            }

            if (hp <= dm) {

                if(!p.getPassengers().isEmpty()){
                    Player znokautowany = (Player) p.getPassengers().get(0);
                    PlayerUtility.setState(znokautowany, Nokaut.LAY);
                    p.getPassengers().clear();
                    PoseUtility.changeGameMode(znokautowany, p, false);
                }

                p.setFireTicks(0);
                p.setHealth(2);

                PoseUtility.start(p);

                if (Main.getInstance().getConfig().getString("BlindnessOnNokaut").equals("true")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 100));
                }

                if (p.getHealth() <= 10.0) {
                    p.setHealth(10.0);
                }
                p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("helpnokautmessage"));
                Runnables.nokautTimer(p);

                List<Entity> nearbyEntities = p.getNearbyEntities(50, 50, 50);
                for (Entity nearbyEntity : nearbyEntities) {
                    if(nearbyEntity instanceof Mob){
                        Mob mob = (Mob) nearbyEntity;
                        if(mob.getTarget() == null)continue;
                        if(mob.getTarget().getUniqueId().equals(p.getUniqueId()))mob.setTarget(null);
                    }
                }

                return true;
            }
        }
        return false;
    }

    public static Entity getLastDamager(Player player){
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        if(lastDamageCause instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
            return damageByEntityEvent.getDamager();
        }
        return null;
    }
}
