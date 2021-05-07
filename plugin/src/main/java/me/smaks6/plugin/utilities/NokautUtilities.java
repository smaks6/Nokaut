package me.smaks6.plugin.utilities;

import me.smaks6.api.Enum.NokautEnum;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import me.smaks6.plugin.service.CitizensListener;
import me.smaks6.plugin.service.WorldGuardFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.smaks6.plugin.Main.gracze;

public class NokautUtilities {

    public static boolean doNokaut(Entity e, double dmm) {
        if (CitizensListener.isNpc(e)) {
            return false;
        }
        if (e instanceof Player) {
            Player p = (Player) e;

            if(p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) ||
                    p.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING))return false;

            if(Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
                if (WorldGuardFlag.isFlag(p)) return false;
            }


            int hp = (int) p.getHealth();
            int dm = (int) dmm;
            NokautEnum hashmap = gracze.get(p);
            if (!hashmap.equals(NokautEnum.STOI)) {
                return false;
            }
            if (hp <= dm) {

                if(!p.getPassengers().isEmpty()){
                    Player znokautowany = (Player) p.getPassengers().get(0);
                    gracze.replace(znokautowany, NokautEnum.LEZY);
                    p.getPassengers().clear();
                    Pose.changegamemode(znokautowany, p, false);
                }

                gracze.replace(p, NokautEnum.LEZY);
                p.setFireTicks(0);
                p.setHealth(2);

                Pose.start(p);

                if (Main.getInstance().getConfig().getString("BlindnessOnNokaut").equals("true")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 100));
                }

                if (p.getHealth() <= 10.0) {
                    p.setHealth(10.0);
                }
                p.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("helpnokautmessage"));
                Runnables.nokautTimer(p);

                return true;
            }
        }
        return false;
    }
}
