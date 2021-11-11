package me.smaks6.api;

import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.entity.Player;

public interface NokautAPI{
    static boolean isKnocked(Player player) {
        return !PlayerUtility.isNull(player);
    }
}
