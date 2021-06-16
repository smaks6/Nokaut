package me.smaks6.api;

import me.smaks6.plugin.utilities.PlayerUtilities;
import org.bukkit.entity.Player;

public interface NokautAPI{
    static boolean isKnocked(Player player) {
        return !PlayerUtilities.isNull(player);
    }
}
