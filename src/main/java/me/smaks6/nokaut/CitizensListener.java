package me.smaks6.nokaut;

import org.bukkit.entity.Entity;

public class CitizensListener {
    public CitizensListener() {
    }

    public static boolean isNpc(Entity e) {
        return e.hasMetadata("NPC");
    }
}
