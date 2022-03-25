package me.smaks6.plugin.utilities;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.service.UpdateCheckerService;
import me.smaks6.plugin.utilities.Enum.NokautError;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public final class ChatUtility{
    public static void checkVersion(){
        new UpdateCheckerService(85152).getVersion(version -> {
            if (Main.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "You have the latest version of the plugin");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Nokaut plugin BY smaks6");
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You don't have the latest plugin version");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "\\          /");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " \\        /");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  \\      /");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "   \\    /");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "    \\  /");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "     \\/");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Nokaut plugin BY smaks6");
            }
        });
    }

    public static void sendDenyMessage(CommandSender sender){
        String cancelmessage = Main.getInstance().getConfig().getString("cancelmessage");
        if(cancelmessage.equals(""))return;
        //sender.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
        sendConfigMessageWithColors("cancelmessage", sender);
    }


    public static void sendConfigMessageWithColors(String configName, CommandSender sender){
        String config = Main.getInstance().getConfig().getString(configName);
        if(config == null){
            sendErrorMessage(NokautError.CHAT_ERROR);
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config));
    }

    public static void sendErrorMessage(NokautError nokautError){sendErrorMessage(nokautError, null);}
    public static void sendErrorMessage(NokautError nokautError, String stacktrace){
        if(stacktrace == null)stacktrace = "";
        Bukkit.getConsoleSender().sendMessage(String.valueOf(nokautError));
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "!!!NOKAUT, PLUGIN ERROR!!!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This is plugin error, please report it on discord");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR message:");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "https://discord.gg/3Uhs9C2");
        System.out.println(stacktrace);

        for (OfflinePlayer operator : Bukkit.getOperators()) {
            if(operator.isOnline()){
                operator.getPlayer().sendMessage(ChatColor.RED + "!!!NOKAUT, PLUGIN ERROR!!!");
                operator.getPlayer().sendMessage(ChatColor.RED + "This is plugin error, please report it on discord");
                operator.getPlayer().sendMessage(ChatColor.RED + "ERROR message:");
                operator.getPlayer().sendMessage(String.valueOf(nokautError));
                operator.getPlayer().sendMessage(ChatColor.RED + "https://discord.gg/3Uhs9C2");
            }
        }
    }

    private ChatUtility(){}
}
