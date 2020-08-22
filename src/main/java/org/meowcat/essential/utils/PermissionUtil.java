package org.meowcat.essential.utils;

import org.bukkit.command.CommandSender;
import org.meowcat.essential.Main;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class PermissionUtil {
    public static String ADMIN = "admin";
    public static String TPA_ACCEPT = "tpa.accept";
    public static String TPA_DENY = "tpa.deny";
    public static String TPA_TPA = "tpa.tpa";
    public static String TPA_HERE = "tpa.here";
    public static String TPA_CANCEL = "tpa.cancel";
    public static String TPA_RANDOM = "tpa.random";
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
    public static String HOME_SET = "home.set";
    public static String HOME_BACK = "home.back";
    public static String COLOR_CHAT = "color.chat";
    public static String COLOR_SIGN = "color.sign";
    public static String SKIN_CHANGE = "skin.change";
    private static final String packageName = "org.meowcat.essential";

    public static boolean hasPermission(CommandSender player, String permission) {
        if (Main.plugin.getConfig().getBoolean("configuration.permissions-enabled", true)) {
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
