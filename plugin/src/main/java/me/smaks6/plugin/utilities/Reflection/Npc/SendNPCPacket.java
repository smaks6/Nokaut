package me.smaks6.plugin.utilities.Reflection.Npc;

import me.smaks6.plugin.utilities.ReflectionUtilities.PacketSender;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class SendNPCPacket {
    private static String version = Reflection.getVersion();
    private Player showTo;
    private Object entityPlayer;

    public SendNPCPacket(Object entityPlayer, Player showTo){
        this.showTo = showTo;
        this.entityPlayer = entityPlayer;
    }

    public void sendPackets(){
        try {
            sendPlayOutPlayerInfo();
            sendNamedEntitySpawn();
            sendWatcher();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private Enum<?> getEnum(int i){
        Enum<?> ADD_PLAYER = (Enum<?>) Reflection.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoA+ction").getEnumConstants()[i];

        return ADD_PLAYER;
    }

    private void sendNamedEntitySpawn() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> packetNamedEntitySpawnClass = Reflection.getNMSClass("PacketPlayOutNamedEntitySpawn");
        Class<?> entityHumanClass = Reflection.getNMSClass("EntityHuman");
        Object NamedEntitySpawnPacket = packetNamedEntitySpawnClass.getConstructor(entityHumanClass).newInstance(entityPlayer);
        PacketSender.sendPacket(showTo, NamedEntitySpawnPacket);
    }

    private void sendPlayOutPlayerInfo() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<?> playOutPlayerInfoClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo");

        final Object table = Array.newInstance(entityPlayer.getClass(), 1);
        Array.set(table, 0, entityPlayer);

        Enum<?> packetEnum = getEnum(0);

        Constructor<?> constructor = playOutPlayerInfoClass.getConstructor(packetEnum.getClass(), table.getClass());

        Object playOutPlayerInfoPacket = constructor.newInstance(packetEnum, table);

        PacketSender.sendPacket(showTo, playOutPlayerInfoPacket);
    }

    private void sendWatcher() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Object DataWatcher = Reflection.getMethod(entityPlayer.getClass(), "getDataWatcher").invoke(entityPlayer);
        int id = (int) Reflection.getMethod(entityPlayer.getClass(), "getId").invoke(entityPlayer);

        Object playOutEntityMetadataPacket = Reflection.getNMSClass("PacketPlayOutEntityMetadata")
                .getConstructor(int.class, DataWatcher.getClass(), boolean.class).newInstance(id, DataWatcher, false);

        PacketSender.sendPacket(showTo, playOutEntityMetadataPacket);
    }

    public void sendTeleportPacket() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> entityClass = Reflection.getNMSClass("Entity");
        Object playOutEntityTeleportPacket = Reflection.getNMSClass("PacketPlayOutEntityTeleport").getConstructor(entityClass).newInstance(entityPlayer);

        PacketSender.sendPacket(showTo, playOutEntityTeleportPacket);
    }

    public void sendDelatePacket() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        int id = (int) Reflection.getMethod(entityPlayer.getClass(), "getId").invoke(entityPlayer);
        int[] idList = new int[1];
        idList[0] = id;
        Object playOutEntityDestroyPacket = Reflection.getNMSClass("PacketPlayOutEntityDestroy").getConstructor(idList.getClass()).newInstance(idList);

        PacketSender.sendPacket(showTo, playOutEntityDestroyPacket);

        //PlayOutPlayerInfo send packet ↓

        Class<?> playOutPlayerInfoClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo");

        final Object table = Array.newInstance(entityPlayer.getClass(), 1);
        Array.set(table, 0, entityPlayer);

        Enum<?> packetEnum = getEnum(4);

        Constructor<?> constructor = playOutPlayerInfoClass.getConstructor(packetEnum.getClass(), table.getClass());

        Object playOutPlayerInfoPacket = constructor.newInstance(packetEnum, table);

        PacketSender.sendPacket(showTo, playOutPlayerInfoPacket);
    }
}
