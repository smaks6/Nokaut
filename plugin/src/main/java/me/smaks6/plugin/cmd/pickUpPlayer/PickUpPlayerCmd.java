package me.smaks6.plugin.cmd.pickUpPlayer;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PickUpPlayerCmd implements CommandExecutor {


    public PickUpPlayerCmd(Main main) {
        main.getInstance().getCommand("pickuupplayer").setExecutor(this);
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
                Player knockedPlayer = (Player) e;

                if(PlayerUtilities.getEnum(knockedPlayer).equals(Nokaut.LAY)){
                    PlayerUtilities.setEnum(knockedPlayer, Nokaut.CARRY);
                    Pose.changegamemode(knockedPlayer, sender, true);
                    sender.addPassenger(knockedPlayer);
                    break;
                }
            }
        }

        return false;
    }
}
