package me.smaks6.plugin.utilities.Reflection.New.GameMode;

import me.smaks6.plugin.utilities.ReflectionUtilities.PacketSender;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EnumGamemode;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class ChangeGameModeNew {
    public static void changeGameMode(Player knockedPlayer, Player reviever, boolean nies){
        //nies:
        //true - na plecach niech idzie
        //false - niech już spada z pleców
        EntityPlayer knockedEntity = (EntityPlayer) Reflection.getEntityPlayer(knockedPlayer);

        if(nies){
            knockedPlayer.setGameMode(GameMode.SPECTATOR);
            knockedEntity.setSpectatorTarget((Entity) reviever);
            knockedEntity.d.setGameMode(EnumGamemode.c);

        }else {
            knockedPlayer.setGameMode(GameMode.SPECTATOR);
            knockedPlayer.setSpectatorTarget(null);
            knockedPlayer.setGameMode(GameMode.SURVIVAL);

            knockedEntity.d.setGameMode(EnumGamemode.c);

            try {
                PacketSender.sendPacket(knockedPlayer, new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.d, 0));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }
}
