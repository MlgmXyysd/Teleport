package org.meowcat.essential.utils;


import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class NMSUtil {

    public static final Class<?> whiteListEntry;
    public static final Method whitelist;
    public static final Method add;
    private static final Class<?> playerList;
    private static final Class<?> craftServer;
    private static final Class<?> jsonList;
    private static final Method handle;

    static {
        playerList = getClass("net.minecraft.server." + getServerVersion() + ".PlayerList");
        whiteListEntry = getClass("net.minecraft.server." + getServerVersion() + ".WhiteListEntry");
        craftServer = getClass("org.bukkit.craftbukkit." + getServerVersion() + ".CraftServer");
        jsonList = getClass("net.minecraft.server." + getServerVersion() + ".JsonList");
        handle = getMethod(craftServer, "getHandle");
        whitelist = getMethod(playerList, "getWhitelist");
        add = getMethodIgnoreParam().get(0);
    }

    public static Object getNMSPlayerList(Server pServer) {
        if (handle.getDeclaringClass().isInstance(pServer)) {
            return invokeMethod(handle, pServer);
        }
        return null;
    }

    public static <T> T newInstance(Class<? extends T> pClass, Class<?> pParamType, Object pParam) {
        return newInstance(pClass, new Class<?>[]{pParamType}, new Object[]{pParam});
    }

    private static <T> T newInstance(Class<? extends T> pClass, Class<?>[] pParamTypes, Object[] pParams) {
        try {
            Constructor<? extends T> tCons;
            if (pParamTypes == null || pParamTypes.length == 0)
                tCons = pClass.getDeclaredConstructor();
            else tCons = pClass.getDeclaredConstructor(pParamTypes);
            tCons.setAccessible(true);
            return tCons.newInstance(pParams);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException exp) {
            throw new IllegalStateException("Error", exp);
        }
    }


    private static ArrayList<Method> getMethodIgnoreParam() {
        ArrayList<Method> tMethods = new ArrayList<>();
        for (Method sMethod : NMSUtil.jsonList.getDeclaredMethods()) {
            if (sMethod.getName().equals("add")) {
                tMethods.add(sMethod);
            }

        }

        if (!tMethods.isEmpty()) {
            return tMethods;
        }

        throw new IllegalStateException("Error", new NoSuchMethodException());
    }

    private static Method getMethod(Class<?> pClass, String pMethodName) {
        return getMethod(pClass, pMethodName, new Class<?>[0]);
    }

    private static Method getMethod(Class<?> pClass, String pMethodName, Class<?>[] pParamsTypes) {
        for (Method sMethod : pClass.getDeclaredMethods()) {
            if (sMethod.getName().equals(pMethodName) && Arrays.equals(sMethod.getParameterTypes(), pParamsTypes)) {
                return sMethod;
            }
        }

        throw new IllegalStateException("Error", new NoSuchMethodException());
    }

    public static Object invokeMethod(Method pMethod, Object pObj, Object... pParams) {
        try {
            pMethod.setAccessible(true);
            return pMethod.invoke(pObj, pParams);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException exp) {
            throw new IllegalStateException("Error", exp);
        }
    }

    private static Class<?> getClass(String pClass) {
        try {
            return Class.forName(pClass);
        } catch (ClassNotFoundException exp) {
            throw new IllegalStateException("Error", exp);
        }
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);
    }
}
