package me.smaks6.v1_15_R1;

import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.EnumGamemode;
import net.minecraft.server.v1_16_R1.PacketPlayOutGameStateChange;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class OtherMetchod {

    public static void changeGameMode(Player znokautowany, Player reviever, boolean nies){
        //nies:
        //true - na plecach niech idzie
        //false - niech już spada z pleców
        EntityPlayer knockedEntity = ((CraftPlayer) znokautowany).getHandle();

        if(nies){
            znokautowany.setGameMode(GameMode.SPECTATOR);
            znokautowany.setSpectatorTarget(reviever);
            knockedEntity.playerInteractManager.setGameMode(EnumGamemode.ADVENTURE);

        }else {
            znokautowany.setGameMode(GameMode.SPECTATOR);
            znokautowany.setSpectatorTarget(null);
            znokautowany.setGameMode(GameMode.SURVIVAL);

            knockedEntity.playerInteractManager.setGameMode(EnumGamemode.ADVENTURE);
            knockedEntity.playerConnection.sendPacket(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.d, 0));
        }

    }
}
