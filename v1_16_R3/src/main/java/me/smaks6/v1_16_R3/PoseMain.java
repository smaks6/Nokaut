package me.smaks6.v1_16_R3;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PoseMain {

    private static final HashMap<Player, EntityPlayer> body = new HashMap<Player, EntityPlayer>();

    private static Server server = null;

    public static void startPose(Player p, Server serve){
        server = serve;
        EntityPlayer npc = spawnnpc(p.getLocation());
        hidePlayer(p);
        body.put(p, npc);
    }

    public static void stopPose(Player p, Server serve){
        server = serve;
        removenpc(body.get(p), p);
        showPlayer(p);
        body.remove(p);
    }

    public static void tpPlayerToPlayer(Player sender, Player znokautowany){
        EntityPlayer npc = body.get(znokautowany);
        Location loc = sender.getLocation().add(0, 2, 0);
        BlockPosition blockPos = new BlockPosition(loc.getBlock().getX(), loc.getBlock().getY(), loc.getBlock().getZ());
        WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
        npc.teleportTo(worldServer, blockPos);

    }

    private static EntityPlayer spawnnpc(Location location){
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld("world")).getHandle(); // Change "world" to the world the NPC should be spawned in.
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.RED + "*Nokaut*"); // Change "playername" to the name the NPC should have, max 16 characters.
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.setPose(EntityPose.SWIMMING);

        for(Player player : server.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc)); // Spawns the NPC for the player client.
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
            DataWatcher watcher = npc.getDataWatcher();
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, false));
            npc.setPose(EntityPose.SWIMMING);
        }

        
        return npc;
    }

    private static void removenpc(EntityPlayer npc, Player p){
        for(Player player : server.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
        }
    }

    private static void hidePlayer(Player p){
        for (Player player : server.getOnlinePlayers()){
            player.hidePlayer(p);
        }
    }

    private static void showPlayer(Player p){
        for (Player player : server.getOnlinePlayers()){
            player.showPlayer(p);
        }
    }


}
