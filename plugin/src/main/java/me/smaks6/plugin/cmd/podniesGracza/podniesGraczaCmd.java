package me.smaks6.plugin.cmd.podniesGracza;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.pose;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static me.smaks6.plugin.Main.gracze;

public class podniesGraczaCmd implements CommandExecutor {

    public podniesGraczaCmd(Main main) {
        Main.getInstance().getCommand("podniesgracza").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("You are console!");
        }


        Player sender = (Player) commandSender;

        if(!gracze.get(sender).equals("stoi") || !sender.getPassengers().isEmpty()){
            return false;
        }

        Entity[] entities = sender.getNearbyEntities(1, 3, 1).toArray(new Entity[0]);


        for(Entity e : entities){
            if(e instanceof Player){
                Player znokautowany = (Player) e;

                if(gracze.get(znokautowany).equals("lezy")){
                    gracze.replace(znokautowany, "nies");
                    przenoszenie(sender, znokautowany);

                    //dodaj by gracz się za nim teleportował

                    break;
                }

            }
        }




        return false;
    }


    public void przenoszenie(Player sender, Player znokautowany){
        new BukkitRunnable() {

            @Override
            public void run() {
                pose.tpPlayerToPlayer(sender, znokautowany);

                if(!sender.isOnline() || !znokautowany.isOnline()){
                    cancel();
                    return;
                }

                if(!gracze.get(znokautowany).equals("nies")){
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 1L);
    }
}
