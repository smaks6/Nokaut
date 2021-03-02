package me.smaks6.nokaut.pose;

import me.smaks6.nokaut.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static me.smaks6.nokaut.Main.gracze;

public class pose {

    public static void start(Player p) {
        List<Player> others = new ArrayList<>();

        for (Player po : Bukkit.getOnlinePlayers()){
            others.add(po);
        }

        p.setSwimming(true);
        p.setSprinting(true);
        gracze(p);
        p.setWalkSpeed(0);
        p.setAllowFlight(true);
        p.setFlying(false);
        p.setFlySpeed(0);
        p.setDisplayName(me.smaks6.nokaut.Main.getInstance().getConfig().getString("NokautTitle") + p.getName());
        p.setCustomNameVisible(true);
    }

    public static void stop(Player p){
        p.setSwimming(false);
        p.setSprinting(false);
        gracze.replace(p.getName(), "stoi");
        usunblock(p);

        p.setDisplayName(p.getName());

        Block block = p.getLocation().getBlock().getRelative(BlockFace.UP);
        p.sendBlockChange(block.getLocation(), Material.AIR, (byte)0);

        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.2F);
        p.setAllowFlight(false);

    }

    public static void gracze(Player target){
        new BukkitRunnable() {

            @Override
            public void run() {

                List<Player> others = new ArrayList<>();
                for (Player po : Bukkit.getOnlinePlayers()){
                    others.add(po);
                }

                String hashmap = gracze.get(target.getName());

                if(hashmap.equals("stoi") || !target.isOnline()){
                    cancel();
                    return;
                }


                //Tutaj będzie wykonywanie pozycji!


                if(hashmap.equals("nies"))return;


                Block block = target.getLocation().getBlock().getRelative(BlockFace.UP);
                if(!block.getType().isSolid()){
                    target.sendBlockChange(block.getLocation(), Material.BARRIER, (byte)0);
                }


            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
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
