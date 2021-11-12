package me.smaks6.plugin.utilities.Reflection.Old.Npc;

import com.mojang.authlib.GameProfile;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import me.smaks6.plugin.utilities.Runnables;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class Npc {

    private static Class<?> entityPlayerClass = Reflection.getNMSClass("EntityPlayer");;
    private static Class<?> worldServerClass = Reflection.getNMSClass("WorldServer");
    private static Class<?> playerInteractManagerClass = Reflection.getNMSClass("PlayerInteractManager");
    private static Class<?> entityPoseClass = Reflection.getNMSClass("EntityPose");
    private static Class<?> minecraftServerClass = Reflection.getNMSClass("MinecraftServer");

    private final static Method getProfileMethod = Reflection.getMethod(entityPlayerClass, "getProfile");

    private Object entityPlayer;

    private Player knockedPlayer;
    private Player toShowPlayer;
    private SendNPCPacket sendNPCPacket;

    public Npc(Player knockedPlayer, Player toShowPlayer) {

        this.knockedPlayer = knockedPlayer;
        this.toShowPlayer = toShowPlayer;


        try {
            Object nmsServer = Reflection.getNMSServer();

            Object nmsWorld = Reflection.getNMSWorld();

            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
            Object entityKnockedPlayer = Reflection.getEntityPlayer(knockedPlayer);
            GameProfile playerGameProfile = (GameProfile) getProfileMethod.invoke(entityKnockedPlayer);
            gameProfile.getProperties().putAll(playerGameProfile.getProperties());
//            gameProfile.getProperties().putAll(((CraftPlayer) knockedPlayer).getHandle().getProfile().getProperties());

            entityPlayer = getEntityPlayer(nmsServer, nmsWorld, gameProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendNPCPacket = new SendNPCPacket(entityPlayer, toShowPlayer);
        teleportEntity(-0.1);
        sendNPCPacket.sendPackets();

        Runnables.npcTimer(knockedPlayer, this);
    }


    public void teleportEntity(Double teleportHeight) {
        try {
            Location loc = knockedPlayer.getLocation();
            Method setLocation = entityPlayer.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            setLocation.invoke(entityPlayer, loc.getX(), loc.getY() + teleportHeight, loc.getZ(), 0, 0);
            sendNPCPacket.sendTeleportPacket();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void deleteEntity(){
        try {
            sendNPCPacket.sendDelatePacket();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private Object getEntityPlayer(Object nmsServer, Object nmsWorld, GameProfile gameProfile) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        Constructor<?> minecraftServerConstructor = playerInteractManagerClass.getConstructor(worldServerClass);

        Object playerInteractManager = minecraftServerConstructor.newInstance(nmsWorld);

        Constructor<?> constructor = entityPlayerClass.getConstructor
                (minecraftServerClass, worldServerClass, GameProfile.class, playerInteractManagerClass);

        Object entityPlayer = constructor.newInstance(nmsServer, nmsWorld, gameProfile, playerInteractManager);

        entityPlayer.getClass().getMethod("setPose", entityPoseClass).invoke(entityPlayer, entityPoseClass.getEnumConstants()[3]);

        return entityPlayer;
    }
}