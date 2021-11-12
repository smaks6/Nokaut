package me.smaks6.plugin.objects;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class ArmorStandNokaut {

    private static final Double FIRST_LINE_LOCATION = -1.40;
    private static final Double SECOND_LINE_LOCATION = -1.65;

    private final Player knockedPlayer;
    private final ArmorStand firstArmorStand; // with nokaut title
    private final ArmorStand secondArmorStand; // with player name

    public ArmorStandNokaut(Player knockedPlayer) {
        this.knockedPlayer = knockedPlayer;
        this.firstArmorStand = spawnArmorStand(ChatColor.RED + ""+ChatColor.BOLD + Main.getInstance().getConfig().getString("NokautTitleAbovePlayer"), FIRST_LINE_LOCATION);
        this.secondArmorStand = spawnArmorStand(knockedPlayer.getDisplayName(), SECOND_LINE_LOCATION);
    }

    private org.bukkit.entity.ArmorStand spawnArmorStand(String title, double y){
        ArmorStand armorStand = (ArmorStand) knockedPlayer.getWorld().spawnEntity(knockedPlayer.getLocation().add(0, y, 0), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomName(title);
        armorStand.setCustomNameVisible(true);
        setData(armorStand);
        return armorStand;
    }

    public void teleportArmorStands(){
        new BukkitRunnable() {

            @Override
            public void run() {
                if(PlayerUtility.isNull(knockedPlayer) || !knockedPlayer.isOnline()){
                    firstArmorStand.remove();
                    secondArmorStand.remove();
                    cancel();
                }else {
                    Location playerLocation = knockedPlayer.getLocation();

                    Nokaut playerState = PlayerUtility.getState(knockedPlayer);
                    if (playerState != null && playerState.equals(Nokaut.CARRY)) {
                        firstArmorStand.teleport(playerLocation.add(0, -0.20, 0));
                        secondArmorStand.teleport(playerLocation.add(0,  -1.65, 0));
                    } else {
                        firstArmorStand.teleport(playerLocation.add(0, FIRST_LINE_LOCATION, 0));
                        secondArmorStand.teleport(playerLocation.add(0, SECOND_LINE_LOCATION, 0));
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 5L);
    }

    private static void setData(ArmorStand armorStand){
        armorStand.getPersistentDataContainer().set(new NamespacedKey(
                Bukkit.getServer().getPluginManager().getPlugin("nokaut"), "Nokaut"), PersistentDataType.STRING, "nokaut");
    }

    public static boolean isNokautArmorStand(Entity entity){
        if(entity.getPersistentDataContainer().get(new NamespacedKey(
                Bukkit.getServer().getPluginManager().getPlugin("nokaut"), "Nokaut"), PersistentDataType.STRING) == null){
            return false;
        }
        return true;
    }
}
