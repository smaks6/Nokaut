package me.smaks6.plugin.utilities.ReflectionUtilities;

import org.bukkit.Bukkit;
import java.lang.reflect.Method;

public interface Reflection {

    static Class<?> getNMSClass(String nmsClassName) {
        try {
            if(getVersion().equals("v1_16_R3") || getVersion().equals("v1_16_R2") || getVersion().equals("v1_16_R1")){
                return Class.forName("net.minecraft.server." + getVersion() + "." + nmsClassName);
            }else{
                return Class.forName("net.minecraft.server." + nmsClassName);
            }
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
