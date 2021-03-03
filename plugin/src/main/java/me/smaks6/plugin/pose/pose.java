package me.smaks6.plugin.pose;

import me.smaks6.plugin.Main;
import me.smaks6.v1_16_R3.PoseMain;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.smaks6.plugin.Main.gracze;

public class pose {

    public static void start(Player p) {
        p.setWalkSpeed(0);
        p.setFlySpeed(0);

        PoseMain.startPose(p, Main.getInstance().getServer());

        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 100, false));

    }

    public static void stop(Player p){
        gracze.replace(p.getName(), "stoi");

        p.setDisplayName(p.getName());

        Block block = p.getLocation().getBlock().getRelative(BlockFace.UP);
        p.sendBlockChange(block.getLocation(), Material.AIR, (byte)0);

        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.1F);
        PoseMain.stopPose(p, Main.getInstance().getServer());

        p.removePotionEffect(PotionEffectType.INVISIBILITY);
    }


    public static void tpPlayerToPlayer(Player sender, Player znokautowany){
        PoseMain.tpPlayerToPlayer(sender, znokautowany);
    }



}
