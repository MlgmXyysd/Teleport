package org.meowcat.essential.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.meowcat.essential.Main;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LocationUtil {
    public static final String TYPE_HOME = "home";
    public static final String TYPE_BACK = "back";
    private static File homeFile;
    private static YamlConfiguration homeYaml;

    public LocationUtil() {
        homeFile = new File(Main.plugin.getDataFolder(), "home.yml");
        homeYaml = YamlConfiguration.loadConfiguration(homeFile);
    }

    public static void setHome(Player player, String type) {
        UUID playerUUID = PlayerStatusUtil.getOfflineUUID(player.getDisplayName());
        homeYaml.set(type + "." + playerUUID.toString() + ".X", player.getLocation().getX());
        homeYaml.set(type + "." + playerUUID.toString() + ".Y", player.getLocation().getY());
        homeYaml.set(type + "." + playerUUID.toString() + ".Z", player.getLocation().getZ());
        homeYaml.set(type + "." + playerUUID.toString() + ".Yaw", player.getLocation().getYaw());
        homeYaml.set(type + "." + playerUUID.toString() + ".Pitch", player.getLocation().getPitch());
        homeYaml.set(type + "." + playerUUID.toString() + ".World", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        saveHomeFile();
    }

    private static void saveHomeFile() {
        try {
            homeYaml.save(homeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void teleportHome(Player player, String type) {
        UUID playerUUID = PlayerStatusUtil.getOfflineUUID(player.getDisplayName());
        Location home = new Location(Bukkit.getWorld(Objects.requireNonNull(homeYaml.getString(type + "." + playerUUID.toString() + ".World"))), homeYaml.getDouble(type + "." + playerUUID.toString() + ".X"), homeYaml.getDouble(type + "." + playerUUID.toString() + ".Y"), homeYaml.getDouble(type + "." + playerUUID.toString() + ".Z"), (float) homeYaml.getLong(type + "." + playerUUID.toString() + ".Yaw"), (float) homeYaml.getLong(type + "." + playerUUID.toString() + ".Pitch"));
        if (PermissionUtil.hasPermission(player, PermissionUtil.HOME_BACK)) {
            LocationUtil.setHome(player, "back");
        }
        player.teleport(home);
    }

    public static boolean homeIsNull(Player player, String type) {
        return homeYaml.getString(type + "." + PlayerStatusUtil.getOfflineUUID(player.getDisplayName()).toString()) == null;
    }
}

