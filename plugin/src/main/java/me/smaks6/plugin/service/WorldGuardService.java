package me.smaks6.plugin.service;

import com.google.common.base.Preconditions;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.BooleanFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.entity.Player;

public class WorldGuardService {

    private static final Flag<Boolean> REGION_FLAG = new BooleanFlag("nokautSTOP");
    private static Flag<Boolean> regionFlag;

    public void registerFlag() {
        FlagRegistry flagRegistry = this.getWorldGuard().getFlagRegistry();
        Flag<?> flag = flagRegistry.get(REGION_FLAG.getName());
        if (flag == null) {
            flag = REGION_FLAG;
            flagRegistry.register(flag);
        }

        this.regionFlag = (Flag<Boolean>) flag;
    }

    public static boolean isFlag(Player p) {
        Preconditions.checkState(regionFlag != null, "Flaga nie zarejestrowała się");
        WorldGuardPlatform platform = getWorldGuard().getPlatform();
        if (platform == null) {
            return false;
        } else {
            RegionContainer regionContainer = platform.getRegionContainer();
            if (regionContainer == null) {
                return false;
            } else {
                Location location = BukkitAdapter.adapt(p.getLocation());
                location = location.setY(Math.max(0.0D, Math.min(255.0D, location.getY())));
                ApplicableRegionSet regions = regionContainer.createQuery().getApplicableRegions(location);
                Boolean value = regions.queryValue(WorldGuardPlugin.inst().wrapPlayer(p), regionFlag);
                return value != null && value;
            }
        }
    }

    private static com.sk89q.worldguard.WorldGuard getWorldGuard() {
        return com.sk89q.worldguard.WorldGuard.getInstance();
    }
}
