package me.smaks6.plugin.cmd.rzucgracza;

import me.smaks6.plugin.Main;
import me.smaks6.api.NokautEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.smaks6.plugin.pose.pose;

import static me.smaks6.plugin.Main.gracze;

public class rzucgraczaCmd implements CommandExecutor {

    public rzucgraczaCmd(Main main) {
        Main.getInstance().getCommand("rzucgracza").setExecutor(this);
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

        pose.changegamemode(player, sender, false);

        player.teleport(sender);

        gracze.replace(player, NokautEnum.LEZY);



        return false;
    }
}
