package me.smaks6.v1_16_R1;

import com.mojang.authlib.GameProfile;
import me.smaks6.api.NokautEnum;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CreateNPC extends me.smaks6.api.CreateNPC {

    private Player znokautowany;
    private Player see;
    private EntityPlayer entityPlayer;

    private static HashMap<Player, NokautEnum> gracze;

    public CreateNPC(Player znokautowany, Player see, HashMap<Player, NokautEnum> gracze){
        this.gracze = gracze;
        this.znokautowany = znokautowany;
        this.see = see;

        entityPlayer = spawnNPC();
        teleportNPCRunnable();
    }


    private EntityPlayer spawnNPC(){
        Location location = znokautowany.getLocation();
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld(znokautowany.getWorld().getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.RED + "*Nokaut*");
        gameProfile.getProperties().putAll(((CraftPlayer) znokautowany).getHandle().getProfile().getProperties());
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.setPose(EntityPose.SWIMMING);

        PlayerConnection connection = ((CraftPlayer) see).getHandle().playerConnection;
        npc.setPose(EntityPose.SWIMMING);
        DataWatcher watcher = npc.getDataWatcher();
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, false));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

        CraftPlayer craftPlayer = npc.getBukkitEntity();
        craftPlayer.setCollidable(false);

        return npc;
    }

    private void teleportNPc(Double teleportHight){
        entityPlayer.setLocation(znokautowany.getLocation().getX(), znokautowany.getLocation().getY()+teleportHight, znokautowany.getLocation().getZ(),
                entityPlayer.yaw, entityPlayer.pitch);


        PlayerConnection connection = ((CraftPlayer) see).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityTeleport(entityPlayer));
    }


    private void removenpc(){
        PlayerConnection connection = ((CraftPlayer) see).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(entityPlayer.getId()));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer));
    }

    private void teleportNPCRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {


                if(znokautowany.isOnline()) {
                    if (gracze.get(znokautowany).equals(NokautEnum.STOI)) {
                        removenpc();
                        cancel();
                        return;
                    }

                    if (gracze.get(znokautowany).equals(NokautEnum.LEZY)) {
                        teleportNPc(-0.1);
                    }


                    if (gracze.get(znokautowany).equals(NokautEnum.NIES)) {
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
