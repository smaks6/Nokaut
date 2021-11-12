package me.smaks6.plugin.commands;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.PoseUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PickUpPlayerCommand implements CommandExecutor, PluginCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("You are console!");
        }

        Player sender = (Player) commandSender;

        if(!PlayerUtility.isNull(sender) || !sender.getPassengers().isEmpty()){
            return false;
        }

        Entity[] entities = sender.getNearbyEntities(1, 3, 1).toArray(new Entity[0]);


        for(Entity e : entities){
            if(e instanceof Player){
                Player knockedPlayer = (Player) e;

                if(PlayerUtility.getState(knockedPlayer).equals(Nokaut.LAY)){
                    PlayerUtility.setState(knockedPlayer, Nokaut.CARRY);
                    PoseUtility.changegamemode(knockedPlayer, sender, true);
                    sender.addPassenger(knockedPlayer);
                    break;
                }
            }
        }

        return false;
    }

    @Override
    public void enable() {
        Main.getInstance().getCommand("pickuupplayer").setExecutor(this);
    }
}
