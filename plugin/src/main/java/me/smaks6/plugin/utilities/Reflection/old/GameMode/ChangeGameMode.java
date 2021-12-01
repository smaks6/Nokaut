package me.smaks6.plugin.utilities.Reflection.old.GameMode;

import me.smaks6.plugin.utilities.ReflectionUtilities.PacketSender;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChangeGameMode {

    private static Class<?> craftPlayerClass = Reflection.getBukkitClass("entity.CraftPlayer");
    private static Class<?> entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
    private static Method getHandle = Reflection.getMethod(craftPlayerClass, "getHandle");
    private static Class<?> enumGamemode = Reflection.getNMSClass("EnumGamemode");

    public static void changeGameMode(Player knockedPlayer, Player reviever, boolean carry){

        if(carry){
            on(knockedPlayer, reviever);
        }else {
            off(knockedPlayer);
        }

    }


    private static void on(Player knockedPlayer, Player reviever){
        knockedPlayer.setGameMode(GameMode.SPECTATOR);
        knockedPlayer.setSpectatorTarget(reviever);
        try {
            setEnumGamemode(knockedPlayer, 2);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void off(Player knockedPlayer){
        knockedPlayer.setGameMode(GameMode.SPECTATOR);
        knockedPlayer.setSpectatorTarget(null);
        knockedPlayer.setGameMode(GameMode.SURVIVAL);

        try {
            sendStopCameraBugging(knockedPlayer);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static void setEnumGamemode(Player player, int enumNumber) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//        enumNumber can set to:
//        0 - NOT_SET
//        1 - SURVIVAL
//        2 - CREATIVE
//        3 - ADVENTURE
//        4 - SPECTATOR

        Object entityPlayer = getHandle.invoke(player);
        Object playerInteractManager = entityPlayerClass.getField("playerInteractManager").get(entityPlayer);

        Enum<?> playerEnum = (Enum<?>) enumGamemode.getEnumConstants()[enumNumber];

        playerInteractManager.getClass().getMethod("setGameMode", enumGamemode).invoke(playerInteractManager, playerEnum);
    }


    private static void sendStopCameraBugging(Player player) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> playOutGameStateChange$aClass = Reflection.getNMSClass("PacketPlayOutGameStateChange$a");
        Class<?> playOutGameStateChangeClass = Reflection.getNMSClass("PacketPlayOutGameStateChange");
        Object playOutGameStateChangeVariable = playOutGameStateChangeClass.getDeclaredField("d").get(null);

        Object packet = playOutGameStateChangeClass.getConstructor(playOutGameStateChange$aClass, float.class).newInstance(playOutGameStateChangeVariable, 0);
        PacketSender.sendPacket(player, packet);
    }
}
