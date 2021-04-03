package me.smaks6.v1_16_R3;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class OtherMetchod {

    public static void changeGameMode(Player znokautowany, Player reviever, boolean nies){
        //nies:
        //true - na plecach niech idzie
        //false - niech już spada z pleców
        EntityPlayer KnockedEntity = ((CraftPlayer) znokautowany).getHandle();

        if(nies){
            znokautowany.setGameMode(GameMode.SPECTATOR);
            znokautowany.setSpectatorTarget(reviever);
            KnockedEntity.playerInteractManager.setGameMode(EnumGamemode.ADVENTURE);
        }else {
            znokautowany.setGameMode(GameMode.SPECTATOR);
            znokautowany.setSpectatorTarget(null);
            znokautowany.setGameMode(GameMode.SURVIVAL);

            KnockedEntity.playerInteractManager.setGameMode(EnumGamemode.ADVENTURE);
            KnockedEntity.playerConnection.sendPacket(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.d, 0));
        }

    }
}
