package me.smaks6.plugin.utilities.ReflectionUtilities;

import org.bukkit.Bukkit;
import java.lang.reflect.Method;

public interface Reflection {

    static Class<?> getNMSClass(String nmsClassName) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + nmsClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Class<?> getBukkitClass(String bukkitClassName){
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + bukkitClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Method getMethod(Class<?> clas, String methodName){
        try {
            return clas.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    static String getVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
