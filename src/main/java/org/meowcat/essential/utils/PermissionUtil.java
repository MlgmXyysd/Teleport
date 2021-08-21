package org.meowcat.essential.utils;

import org.bukkit.command.CommandSender;
import org.meowcat.essential.Main;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class PermissionUtil {
    public static String ADMIN = "admin";
    public static String TELEPORT_ACCEPT = "teleport.accept";
    public static String TELEPORT_DENY = "teleport.deny";
    public static String TELEPORT_TPA = "teleport.tpa";
    public static String TELEPORT_TPAHERE = "teleport.tpahere";
    public static String TELEPORT_CANCEL = "teleport.cancel";
    public static String TELEPORT_RANDOM = "teleport.random";
    public static String TELEPORT_ALL = "teleport.all";
    public static String TOGGLE_PO = "plusone.toggle";
    public static String TOGGLE_TP = "tpa.toggle";
    public static String TOGGLE_AT = "at.toggle";
    public static String HAT = "hat";
    public static String CLEARENTITY = "clearentity";
    public static String WHITELIST_ADD = "whitelist.add";
    public static String AT_ALL = "at.all";
    public static String AT_PLAYER = "at.player";
    public static String UPDATE = "update";
    public static String HOME_HOME = "home.home";
    public static String HOME_SPAWN = "home.spawn";
    public static String HOME_SET = "home.set";
    public static String HOME_BACK = "home.back";
    public static String COLOR_CHAT = "color.chat";
    public static String COLOR_SIGN = "color.sign";
    public static String SKIN_CHANGE = "skin.change";
    public static String COMMAND_PERFORM = "command.perform";
    public static String PERFORM_SUICIDE = "suicide";
    private static final String packageName = "org.meowcat.essential";

    public static boolean hasPermission(CommandSender player, String permission) {
        return hasPermission(player, permission, false);
    }

    public static boolean hasPermission(CommandSender player, String permission, boolean force) {
        if (Main.plugin.getConfig().getBoolean("configuration.permissions-enabled", true) || force) {
            if (player.hasPermission(packageName + "." + permission) || player.hasPermission(packageName + "." + ADMIN)) {
                return true;
            } else {
                noPermission(player);
                return false;
            }
        } else {
            return true;
        }
    }

    private static void noPermission(CommandSender player) {
        player.sendMessage(LanguageUtil.NO_PERMISSION);
    }
}
