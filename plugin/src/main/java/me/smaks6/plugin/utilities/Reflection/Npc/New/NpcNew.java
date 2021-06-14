package me.smaks6.plugin.utilities.Reflection.Npc.New;

import com.mojang.authlib.GameProfile;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class NpcNew {

    private Player znokautowany;
    private Player see;
    private EntityPlayer entityPlayer;

    public NpcNew(Player znokautowany, Player see){
        this.znokautowany = znokautowany;
        this.see = see;

        entityPlayer = spawnNPC();
        teleportNPCRunnable();
    }

    private EntityPlayer spawnNPC(){
        Location location = znokautowany.getLocation();
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld(znokautowany.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.RED + "*Nokaut*");
        gameProfile.getProperties().putAll(((CraftPlayer) znokautowany).getHandle().getProfile().getProperties());
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile);
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.setPose(EntityPose.SWIMMING);

        PlayerConnection connection = ((CraftPlayer) see).getHandle().b;
        npc.setPose(EntityPose.SWIMMING);
        DataWatcher watcher = npc.getDataWatcher();
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, false));

        CraftPlayer craftPlayer = npc.getBukkitEntity();
        craftPlayer.setCollidable(false);

        return npc;
    }

    private void teleportNPc(Double teleportHight){
        entityPlayer.setLocation(znokautowany.getLocation().getX(), znokautowany.getLocation().getY()+teleportHight, znokautowany.getLocation().getZ(),
                entityPlayer.get, entityPlayer.);


        PlayerConnection connection = ((CraftPlayer) see).getHandle().b;
        connection.sendPacket(new PacketPlayOutEntityTeleport(entityPlayer));
    }

    private void removenpc(){
        PlayerConnection connection = ((CraftPlayer) see).getHandle().b;
        connection.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
    }

    private void teleportNPCRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {


                if(znokautowany.isOnline()) {
                    if (PlayerUtilities.isNull(znokautowany)) {
                        removenpc();
                        cancel();
                        return;
                    }

                    if (PlayerUtilities.getEnum(znokautowany).equals(Nokaut.LAY)) {
                        teleportNPc(-0.1);
                    }

                    if (PlayerUtilities.getEnum(znokautowany).equals(Nokaut.CARRY)) {
                        teleportNPc(1.0);
                    }


                }else {
                    removenpc();
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("nokaut"), 3, 3);
    }


}
