package me.smaks6.plugin.pose;

import me.smaks6.plugin.Main;
import me.smaks6.v1_16_R3.PoseMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.smaks6.plugin.Main.gracze;

public class pose {

    public static void start(Player p) {
        p.setWalkSpeed(0);
        p.setFlySpeed(0);

        PoseMain.startPose(p, Main.getInstance().getServer());

    }

    public static void stop(Player p){
        gracze.replace(p.getName(), "stoi");
        usunblock(p);

        p.setDisplayName(p.getName());

        Block block = p.getLocation().getBlock().getRelative(BlockFace.UP);
        p.sendBlockChange(block.getLocation(), Material.AIR, (byte)0);

        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.1F);
        PoseMain.stopPose(p, Main.getInstance().getServer());

    }


    public static void usunblock(Player target){

        Block up = target.getLocation().getBlock().getRelative(BlockFace.UP);
        Block down = target.getLocation().getBlock().getRelative(BlockFace.DOWN);

        up.getState().update();
        up.getLocation().add(1,0,0).getBlock().getState().update();
        up.getLocation().add(-1,0,0).getBlock().getState().update();
        up.getLocation().add(0,0,1).getBlock().getState().update();
        up.getLocation().add(0,0,-1).getBlock().getState().update();
        up.getLocation().add(1,0,1).getBlock().getState().update();
        up.getLocation().add(-1,0,-1).getBlock().getState().update();
        up.getLocation().add(1,0,-1).getBlock().getState().update();
        up.getLocation().add(-1,0,1).getBlock().getState().update();

        down.getLocation().add(1,0,0).getBlock().getState().update();
        down.getLocation().add(-1,0,0).getBlock().getState().update();
        down.getLocation().add(0,0,1).getBlock().getState().update();
        down.getLocation().add(0,0,-1).getBlock().getState().update();
        down.getLocation().add(1,0,1).getBlock().getState().update();
        down.getLocation().add(-1,0,-1).getBlock().getState().update();
        down.getLocation().add(1,0,-1).getBlock().getState().update();
        down.getLocation().add(-1,0,1).getBlock().getState().update();

    }



}
