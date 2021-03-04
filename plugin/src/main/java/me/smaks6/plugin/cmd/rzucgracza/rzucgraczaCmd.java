package me.smaks6.plugin.cmd.rzucgracza;

import me.smaks6.plugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import static me.smaks6.plugin.Main.gracze;

import java.util.List;

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

        List<Entity> passengers = sender.getPassengers();
        if(passengers.isEmpty()){
            return false;
        }

        for(Entity e : passengers){
            if(e instanceof Player){
                Player passanger = (Player) e;
                if(gracze.get(passanger).equals("nies")){
                    gracze.replace(passanger, "lezy");

                    //dodaj by gracz przestał się za nim teleportować

                    break;
                }
            }
        }

        return false;
    }
}
