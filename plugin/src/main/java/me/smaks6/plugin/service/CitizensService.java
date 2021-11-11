package me.smaks6.plugin.service;

import org.bukkit.entity.Entity;

public final class CitizensService {
    private CitizensService() {}

    public static boolean isNpc(Entity e) {
            return e.hasMetadata("NPC");
    }
}
