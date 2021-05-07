package me.smaks6.plugin.utilities;

import me.smaks6.api.Enum.Nokaut;
import me.smaks6.plugin.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUtilities {

    public static boolean isNull(Player player){
        if(player.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "NokautStatus"), PersistentDataType.STRING) == null){
            return true;
        }
        return false;
    }

    public static Nokaut getEnum(Player player){
        return Nokaut.valueOf(player.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "NokautStatus"), PersistentDataType.STRING));
    }

    public static void setEnum(Player player, Nokaut nokautEnum){
        player.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "NokautStatus"), PersistentDataType.STRING, nokautEnum.toString());
    }
}
