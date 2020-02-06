package org.meowcat.essential.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.meowcat.essential.Main;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class LocationUtil {
    private static File homeFile;
    private static YamlConfiguration homes;

    public LocationUtil() {
        homeFile = new File(Main.plugin.getDataFolder(), "home.yml");
        homes = YamlConfiguration.loadConfiguration(homeFile);
    }

    public static void setHome(Player player, String type) {
        UUID playerUUID = PlayerStatusUtil.getOfflineUUID(player.getDisplayName());
        homes.set(type + "." + playerUUID.toString() + ".X", player.getLocation().getX());
        homes.set(type + "." + playerUUID.toString() + ".Y", player.getLocation().getY());
        homes.set(type + "." + playerUUID.toString() + ".Z", player.getLocation().getZ());
        homes.set(type + "." + playerUUID.toString() + ".Yaw", player.getLocation().getYaw());
        homes.set(type + "." + playerUUID.toString() + ".Pitch", player.getLocation().getPitch());
        homes.set(type + "." + playerUUID.toString() + ".World", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        saveHomeFile();
    }

    private static void saveHomeFile() {
        try {
            homes.save(homeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void teleportHome(Player player, String type) {
        UUID playerUUID = PlayerStatusUtil.getOfflineUUID(player.getDisplayName());
        Location home = new Location(Bukkit.getWorld(Objects.requireNonNull(homes.getString(type + "." + playerUUID.toString() + ".World"))), homes.getDouble(type + "." + playerUUID.toString() + ".X"), homes.getDouble(type + "." + playerUUID.toString() + ".Y"), homes.getDouble(type + "." + playerUUID.toString() + ".Z"), (float) homes.getLong(type + "." + playerUUID.toString() + ".Yaw"), (float) homes.getLong(type + "." + playerUUID.toString() + ".Pitch"));
        player.teleport(home);
    }

    public static boolean homeIsNull(Player player, String type) {
        return homes.getList(type + "." + PlayerStatusUtil.getOfflineUUID(player.getDisplayName())) == null;
    }
}

