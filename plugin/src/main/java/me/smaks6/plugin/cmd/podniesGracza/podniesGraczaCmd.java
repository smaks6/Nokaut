package me.smaks6.plugin.cmd.podniesGracza;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.pose;
import me.smaks6.api.NokautEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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

        if(!gracze.get(sender).equals(NokautEnum.STOI) || !sender.getPassengers().isEmpty()){
            return false;
        }

        Entity[] entities = sender.getNearbyEntities(1, 3, 1).toArray(new Entity[0]);


        for(Entity e : entities){
            if(e instanceof Player){
                Player znokautowany = (Player) e;
                if(gracze.get(znokautowany).equals(NokautEnum.LEZY)){
                    gracze.replace(znokautowany, NokautEnum.NIES);
                    pose.changegamemode(znokautowany, sender, true);
                    sender.addPassenger(znokautowany);
                    break;
                }
            }
        }

        return false;
    }

}
