package me.smaks6.plugin.utilities.ReflectionUtilities;

import com.google.common.reflect.AbstractInvocationHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PacketSender{
    private static Class<?> craftPlayerClass = Reflection.getBukkitClass("entity.CraftPlayer");

    private static Class<?> playerConnectionClass = Reflection.getNMSClass("PlayerConnection");
    private static Class<?> entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
    private static Class<?> packetClass = Reflection.getNMSClass("Packet");
    private static Method getHandle = Reflection.getMethod(craftPlayerClass, "getHandle");
    private static Field playerConnection;
    private static Method sendPacket;

    {
        try {
            sendPacket = playerConnectionClass.getMethod("sendPacket", packetClass);
            playerConnection = entityPlayerClass.getField("playerConnection");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(Player player, Object packet) throws InvocationTargetException, IllegalAccessException {


        Object entityPlayer = getHandle.invoke(player);

        Object connection = playerConnection.get(entityPlayer);

        sendPacket.invoke(connection, packet);
    }
}
