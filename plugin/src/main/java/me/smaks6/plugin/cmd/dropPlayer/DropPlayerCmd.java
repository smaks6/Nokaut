package me.smaks6.plugin.cmd.dropPlayer;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropPlayerCmd implements CommandExecutor {

    public DropPlayerCmd(Main main) {
        Main.getInstance().getCommand("dropplayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("You are console!");
        }
        Player sender = (Player) commandSender;

        if(sender.getPassengers().isEmpty()){
            //nic nie masz
            return false;
        }

        Player player = (Player) sender.getPassengers().get(0);
        sender.getPassengers().clear();
        PlayerUtilities.setEnum(player, Nokaut.LAY);
        Pose.changegamemode(player, sender, false);
        player.teleport(sender);
        return false;
    }
}
