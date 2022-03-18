package me.smaks6.plugin.utilities;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.service.UpdateCheckerService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
        sender.sendMessage(ChatColor.RED + Main.getInstance().getConfig().getString("cancelmessage"));
    }

    private ChatUtility(){}
}
