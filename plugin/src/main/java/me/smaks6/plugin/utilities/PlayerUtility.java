package me.smaks6.plugin.utilities;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUtility {

    public static boolean isNull(Player player){
        if(player.getPersistentDataContainer().get(new NamespacedKey(
                Bukkit.getServer().getPluginManager().getPlugin("nokaut"), "NokautStatus"), PersistentDataType.STRING) == null){
            return true;
        }
        return false;
    }

    public static Nokaut getState(Player player){
        return Nokaut.valueOf(player.getPersistentDataContainer().get(new NamespacedKey(
                Bukkit.getServer().getPluginManager().getPlugin("nokaut"), "NokautStatus"), PersistentDataType.STRING));
    }

    public static void setState(Player player, Nokaut nokautEnum){
        player.getPersistentDataContainer().set(new NamespacedKey(
                Bukkit.getServer().getPluginManager().getPlugin("nokaut"), "NokautStatus"), PersistentDataType.STRING, nokautEnum.toString());
    }

    public static void unSet(Player player) {
        if (!isNull(player)) {
            player.getPersistentDataContainer().remove(new NamespacedKey(
                    Bukkit.getServer().getPluginManager().getPlugin("nokaut"), "NokautStatus"));
        }
    }
}
