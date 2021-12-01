package me.smaks6.plugin.utilities.Reflection.New.Npc;

import com.mojang.authlib.GameProfile;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtility;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class NpcNew {

    private final Player knockedPlayer;
    private final Player see;
    private EntityPlayer entityPlayer;
    private int entityplayerId = 0;

    public NpcNew(Player knockedPlayer, Player see){
        this.knockedPlayer = knockedPlayer;
        this.see = see;

        try {
            entityPlayer = spawnNPC();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        teleportNPCRunnable();
    }

    //ChatColor.RED +
    private EntityPlayer spawnNPC() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Location location = knockedPlayer.getLocation();
        MinecraftServer nmsServer = (MinecraftServer) Reflection.getNMSServer();
        WorldServer nmsWorld = (WorldServer) Reflection.getNMSWorld();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), knockedPlayer.getName());

        if(is1_18()){
            gameProfile.getProperties().putAll((((EntityPlayer)Reflection.getEntityPlayer(knockedPlayer)).fp().getProperties()));
        }else {
            Method method = EntityPlayer.class.getMethod("getProfile");
            GameProfile knockedPlayerGameProfile = (GameProfile) method.invoke(((EntityPlayer) Reflection.getEntityPlayer(knockedPlayer)));
            gameProfile.getProperties().putAll(knockedPlayerGameProfile.getProperties());
        }

        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile);
        setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), npc);
//        npc.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), 40f);

        DataWatcher watcher =  null;

        if(is1_18()){
            entityplayerId = npc.ae();
        }else{
            Method getId = EntityPlayer.class.getMethod("getId");
            entityplayerId = (int) getId.invoke(npc);
        }

        setPose(npc);
        //d - swimming
        if(is1_18()){
            watcher = npc.ai();
        }else {
            Method getDataWatcher = EntityPlayer.class.getMethod("getDataWatcher");
            watcher = (DataWatcher) getDataWatcher.invoke(npc);
        }


        //a - ADD_Player
        //e - REMOVE-PLAYER
        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
        sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        sendPacket(new PacketPlayOutEntityMetadata(entityplayerId, watcher, false));

        return npc;
    }

    private void teleportNPc(Double teleportHight){

        //entityplayer.x = entityplayer.yaw
        //entityplayer.y = entityplayer.pitch

        setLocation(knockedPlayer.getLocation().getX(), knockedPlayer.getLocation().getY()+teleportHight,
                knockedPlayer.getLocation().getZ(), entityPlayer.x, entityPlayer);


        sendPacket(new PacketPlayOutEntityTeleport(entityPlayer));
    }

    private void removeNpc(){
        sendPacket(new PacketPlayOutEntityDestroy(entityplayerId));
        //e - REMOVE-PLAYER
        sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entityPlayer));
    }

    private void sendPacket(Packet<?> packet){
        PlayerConnection connection = ((EntityPlayer)Reflection.getEntityPlayer(see)).b;
        if(is1_18()){
            connection.a(packet);
            return;
        }

        try {
            Method sendPacket = PlayerConnection.class.getMethod("sendPacket", Packet.class);
            sendPacket.invoke(connection, packet);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setLocation(double x, double y, double z, float yaw, EntityPlayer entityPlayerr){
        if(is1_18()){
            entityPlayerr.a(x, y, z, yaw, 40f);
            return;
        }

        try {
            Method setLocation = EntityPlayer.class.getMethod("setLocation", double.class, double.class, double.class,
                    float.class, float.class);

            setLocation.invoke(knockedPlayer, x ,y, z, yaw, 40f);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setPose(EntityPlayer entityPlayer){
        if(is1_18()){
            entityPlayer.a(EntityPose.d);
            return;
        }

        try {
            Method setPose = EntityPlayer.class.getMethod("setPose", EntityPose.class);
            setPose.invoke(entityPlayer, EntityPose.d);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    private void teleportNPCRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {


                if(knockedPlayer.isOnline()) {
                    if (PlayerUtility.isNull(knockedPlayer)) {
                        removeNpc();
                        cancel();
                        return;
                    }

                    if (PlayerUtility.getState(knockedPlayer).equals(Nokaut.LAY)) {
                        teleportNPc(-0.1);
                    }

                    if (PlayerUtility.getState(knockedPlayer).equals(Nokaut.CARRY)) {
                        teleportNPc(1.0);
                    }


                }else {
                    removeNpc();
                    cancel();
                    return;
                }

            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("nokaut"), 3, 3);
    }

    public static boolean is1_18(){
        if(Main.getVersion().equals("v1_17_R1") || Main.getVersion().equals("v1_17_R2"))return false;
        return true;
    }


}
