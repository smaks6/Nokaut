package me.smaks6.plugin.utilities.Reflection.New.GameMode;

import me.smaks6.plugin.utilities.Reflection.New.Npc.NpcNew;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChangeGameModeNew {
    public static void changeGameMode(Player knockedPlayer, Player reviver, boolean throwPlayer){
        //nies:
        //true - na plecach niech idzie
        //false - niech już spada z pleców
        EntityPlayer knockedEntity = (EntityPlayer) Reflection.getEntityPlayer(knockedPlayer);

        if(throwPlayer){
            knockedPlayer.setGameMode(GameMode.SPECTATOR);
            knockedPlayer.setSpectatorTarget(reviver);
            setGameMode(EnumGamemode.c, knockedEntity);
//            knockedEntity.d.a();

        }else {
            knockedPlayer.setGameMode(GameMode.SPECTATOR);
            knockedPlayer.setSpectatorTarget(null);
            knockedPlayer.setGameMode(GameMode.SURVIVAL);

            setGameMode(EnumGamemode.c, knockedEntity);
//            knockedEntity.d.a();

            PlayerConnection connection = ((EntityPlayer)Reflection.getEntityPlayer(knockedPlayer)).b;
            if(NpcNew.is1_18()){
                connection.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.d, 0));
                return;
            }

            try {
                Method sendPacket = PlayerConnection.class.getMethod("sendPacket", Packet.class);
                sendPacket.invoke(connection, new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.d, 0));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
//            connection.a();


        }

    }

    private static void setGameMode(EnumGamemode enumGamemode, EntityPlayer entityPlayer){

        if(NpcNew.is1_18()){
            entityPlayer.a(enumGamemode);
            return;
        }

        try {
            Method setGameMode = PlayerInteractManager.class.getMethod("setGameMode", EnumGamemode.class);
            setGameMode.invoke(entityPlayer, enumGamemode);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
