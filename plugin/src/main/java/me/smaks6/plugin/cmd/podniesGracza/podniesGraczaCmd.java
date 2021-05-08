package me.smaks6.plugin.cmd.podniesGracza;

import me.smaks6.api.Enum.Nokaut;
import me.smaks6.api.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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

        if(!PlayerUtilities.isNull(sender) || !sender.getPassengers().isEmpty()){
            return false;
        }

        Entity[] entities = sender.getNearbyEntities(1, 3, 1).toArray(new Entity[0]);


        for(Entity e : entities){
            if(e instanceof Player){
                Player znokautowany = (Player) e;

                if(PlayerUtilities.getEnum(znokautowany).equals(Nokaut.LAY)){
                    PlayerUtilities.setEnum(znokautowany, Nokaut.CARRY);
                    Pose.changegamemode(znokautowany, sender, true);
                    sender.addPassenger(znokautowany);
                    break;
                }
            }
        }

        return false;
    }

}
