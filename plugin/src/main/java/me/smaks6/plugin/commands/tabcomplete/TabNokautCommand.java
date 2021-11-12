package me.smaks6.plugin.commands.tabcomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class TabNokautCommand implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], Arrays.asList("reload", "wakeup", "nokaut"), completions);
        Collections.sort(completions);
        if(args[0].equalsIgnoreCase("wakeup") || args[0].equalsIgnoreCase("nokaut")){
            List<String> playersNicks = new ArrayList<>();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                playersNicks.add(onlinePlayer.getName());
            }
            return playersNicks;
        }
        return completions;
    }
}