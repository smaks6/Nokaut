package me.smaks6.plugin.commands;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.PoseUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropPlayerCommand implements CommandExecutor, PluginCommand {
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
        PlayerUtility.setState(player, Nokaut.LAY);
        PoseUtility.changegamemode(player, sender, false);
        player.teleport(sender);
        return false;
    }

    @Override
    public void enable() {
        Main.getInstance().getCommand("dropplayer").setExecutor(this);
    }
}
