package me.smaks6.plugin.utilities.Reflection.New.Npc;

import com.mojang.authlib.GameProfile;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class NpcNew {

    private Player knockeckPlayer;
    private Player see;
    private EntityPlayer entityPlayer;

    public NpcNew(Player znokautowany, Player see){
        this.knockeckPlayer = znokautowany;
        this.see = see;

        entityPlayer = spawnNPC();
        teleportNPCRunnable();
    }

    private EntityPlayer spawnNPC(){
        Location location = knockeckPlayer.getLocation();
        MinecraftServer nmsServer = (MinecraftServer) Reflection.getNMSServer();
        WorldServer nmsWorld = (WorldServer) Reflection.getNMSWorld();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.RED + "*Nokaut*");
        gameProfile.getProperties().putAll((((EntityPlayer)Reflection.getEntityPlayer(knockeckPlayer)).getProfile().getProperties()));
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile);
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        //d - swimming
        npc.setPose(EntityPose.d);

        PlayerConnection connection = ((EntityPlayer)Reflection.getEntityPlayer(see)).b;
        npc.setPose(EntityPose.d);
        DataWatcher watcher = npc.getDataWatcher();


        //a - ADD_Player
        //e - REMOVE-PLAYER
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, false));

        return npc;
    }

    private void teleportNPc(Double teleportHight){

        //entityplayer.x = entityplayer.yaw
        //entityplayer.y = entityplayer.pitch

        entityPlayer.setLocation(knockeckPlayer.getLocation().getX(), knockeckPlayer.getLocation().getY()+teleportHight, knockeckPlayer.getLocation().getZ(),
                entityPlayer.x, entityPlayer.y);


        PlayerConnection connection = ((EntityPlayer) Reflection.getEntityPlayer(see)).b;
        connection.sendPacket(new PacketPlayOutEntityTeleport(entityPlayer));
    }

    private void removenpc(){
        PlayerConnection connection = ((EntityPlayer) Reflection.getEntityPlayer(see)).b;
        connection.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
        //e - REMOVE-PLAYER
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entityPlayer));
    }

    private void teleportNPCRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {


                if(knockeckPlayer.isOnline()) {
                    if (PlayerUtilities.isNull(knockeckPlayer)) {
                        removenpc();
                        cancel();
                        return;
                    }

                    if (PlayerUtilities.getEnum(knockeckPlayer).equals(Nokaut.LAY)) {
                        teleportNPc(-0.1);
                    }

                    if (PlayerUtilities.getEnum(knockeckPlayer).equals(Nokaut.CARRY)) {
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
