package org.meowcat.essential.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.meowcat.essential.Main;

import java.io.File;
import java.util.Objects;

public class LanguageUtil {
    private final static String LANGUAGE = Main.plugin.getConfig().getString("configuration.language", "en-us");
    private static final String placeholder = Main.plugin.getConfig().getString("configuration.color-placeholder", "&");
    static final char colorAlt = Objects.requireNonNull(placeholder).charAt(0);

    static String NO_PERMISSION;
    static String PENDING_EXPIRED;
    public static String TPACCEPT_SENDER;
    public static String TPACCEPT_RECEIVER;
    public static String PLAYER_NOT_FOUND;
    public static String NOT_PENDING_REQUEST;
    public static String TPDENY_SENDER;
    public static String TPDENY_RECEIVER;
    public static String TOGGLE_AT_DISABLE;
    public static String TOGGLE_AT_ENABLE;
    public static String TOGGLE_TP_DISABLE;
    public static String TOGGLE_TP_ENABLE;
    public static String TOGGLE_PO_DISABLE;
    public static String TOGGLE_PO_ENABLE;
    public static String PLAYER_ONLY;
    public static String ACTION_NOT_FOUND;
    public static String HAT_NOTHING;
    public static String HAT_ENJOY;
    public static String HAT_CLEAR;
    public static String CLEARENTITY_COMPLETE;
    public static String WHITELIST_ADDED;
    public static String PLAYER_NOT_SELECTED;
    public static String TPA_REQUEST;
    public static String TPA_REQUEST_HERE;
    public static String TPA_ACCEPT;
    public static String TPA_DENY;
    public static String TPA_EXPIRE;
    public static String TPA_YOUSELF;
    public static String TPA_SENDED;
    public static String TPA_CANCEL;
    public static String TPA_DISABLED;
    public static String TPA_CANCEL_NONE;
    public static String TPA_CANCELED_SENDER;
    public static String TPA_CANCELED_RECEIVER;
    public static String TPA_RANDOM_NOT_ALLOWED;
    public static String CLICK_TO_REPEAT;
    public static String AT_ALL;
    public static String AT_PLAYER;
    public static String AT_DISABLED;
    public static String NOT_LATEST;
    public static String UPDATE_DISABLED;
    public static String HOME_SET;
    public static String SPAWN_SET;
    public static String HOME_NOT_SET;
    public static String HOME_WELCOME;
    public static String HOME_BACK;
    public static String HOME_BACK_NOT_SET;
    public static String REPEAT;
    public static String SKIN_HASH_NOT_SELECTED;

    public LanguageUtil() {
        Main.plugin.saveResource("language/" + LANGUAGE + ".yml", false);
        TPACCEPT_SENDER = getLanguage("language.TPACCEPT_SENDER", "&bPlayer &a%s&b accepted your teleport request.");
        TPACCEPT_RECEIVER = getLanguage("language.TPACCEPT_RECEIVER", "&bTeleport request accepted.");
        PLAYER_NOT_FOUND = getLanguage("language.PLAYER_NOTFOUND", "&cPlayer not online or not found.");
        NOT_PENDING_REQUEST = getLanguage("language.NOT_PENDING_REQUEST", "&cYou do not have a pending request.");
        TPDENY_SENDER = getLanguage("language.TPDENY_SENDER", "&cPlayer &a%s&c denied your teleport request.");
        TPDENY_RECEIVER = getLanguage("language.TPDENY_RECEIVER", "&bTeleport request denied.");
        NO_PERMISSION = getLanguage("language.NO_PERMISSION", "&cSorry, you don't have enough permission(s) to do that.");
        PENDING_EXPIRED = getLanguage("language.PENDING_EXPIRED", "&cPending teleport request expired.");
        TOGGLE_AT_DISABLE = getLanguage("language.TOGGLE_AT_DISABLE", "&bYou have disabled personal AT.");
        TOGGLE_AT_ENABLE = getLanguage("language.TOGGLE_AT_ENABLE", "&bYou have enabled personal AT.");
        TOGGLE_TP_DISABLE = getLanguage("language.TOGGLE_TP_DISABLE", "&bYou have disabled teleport.");
        TOGGLE_TP_ENABLE = getLanguage("language.TOGGLE_TP_ENABLE", "&bYou have enabled teleport.");
        TOGGLE_PO_DISABLE = getLanguage("language.TOGGLE_PO_DISABLE", "&bYou have disabled repeater.");
        TOGGLE_PO_ENABLE = getLanguage("language.TOGGLE_PO_ENABLE", "&bYou have enabled repeater.");
        PLAYER_ONLY = getLanguage("language.PLAYER_ONLY", "&cYou only can do that as a player.");
        ACTION_NOT_FOUND = getLanguage("language.ACTION_NOT_FOUND", "&cAction not found.");
        HAT_NOTHING = getLanguage("language.HAT_NOTHING", "&cThere's nothing on your head or hand.");
        HAT_ENJOY = getLanguage("language.HAT_ENJOY", "&bEnjoy your new hat.");
        HAT_CLEAR = getLanguage("language.HAT_CLEAR", "&bYour hat has been removed.");
        CLEARENTITY_COMPLETE = getLanguage("language.CLEARENTITY_COMPLETE", "&bListed entity has killed.");
        WHITELIST_ADDED = getLanguage("language.WHITELIST_ADDED", "&bAdded &a%s&b to whitelist with offline UUID.");
        PLAYER_NOT_SELECTED = getLanguage("language.PLAYER_NOT_SELECTED", "&cYou must select a player to do that.");
        TPA_REQUEST = getLanguage("language.TPA_REQUEST", "&bPlayer &a%s&b has requested to teleport to you.");
        TPA_REQUEST_HERE = getLanguage("language.TPA_REQUEST_HERE", "&bPlayer &a%s&b has requested that you teleport to them.");
        TPA_ACCEPT = getLanguage("language.TPA_ACCEPT", "&bTo accept this request, type &c%s&b or &c%s&b or &c%s&b.");
        TPA_DENY = getLanguage("language.TPA_DENY", "&bTo deny this request, type &c%s&b or &c%s&b or &c%s&b.");
        TPA_EXPIRE = getLanguage("language.TPA_EXPIRE", "&bRequest will expired in 60 seconds.");
        TPA_YOUSELF = getLanguage("language.TPA_YOUSELF", "&cYou can't teleport to yourself.");
        TPA_SENDED = getLanguage("language.TPA_SENDED", "&bA teleport request is sent to &a%s&b.");
        TPA_CANCEL = getLanguage("language.TPA_CANCEL", "&bTo cancel this request, type &c%s&b.");
        TPA_DISABLED = getLanguage("language.TPA_DISABLED", "&cPlayer &a%s&c has disabled teleport.");
        TPA_CANCEL_NONE = getLanguage("language.TPA_CANCEL_NONE", "&cYou do not have a pending request for this player.");
        TPA_CANCELED_SENDER = getLanguage("language.TPA_CANCELED_SENDER", "&bPending teleport request cancelled.");
        TPA_CANCELED_RECEIVER = getLanguage("language.TPA_CANCELED_RECEIVER", "&cPending teleport request cancelled by sender.");
        TPA_RANDOM_NOT_ALLOWED = getLanguage("language.TPA_RANDOM_NOT_ALLOWED", "&cRandom teleport is not allowed in this world.");
        CLICK_TO_REPEAT = getLanguage("language.CLICK_TO_REPEAT", "&bClick to repeat!");
        AT_ALL = getLanguage("language.AT_ALL", "&b[@] You have been called by &a%s&b (All players).");
        AT_PLAYER = getLanguage("language.AT_PLAYER", "&b[@] You have been called by &a%s&b.");
        AT_DISABLED = getLanguage("language.AT_DISABLED", "&cPlayer &b%s&c has disabled call notifications.");
        NOT_LATEST = getLanguage("language.NOT_LATEST", "&ePlugin has a new version, please check it.");
        UPDATE_DISABLED = getLanguage("language.UPDATE_DISABLED", "&eYou disabled plugin update check, please check it manually.");
        HOME_SET = getLanguage("language.HOME_SET", "&bHome point is set.");
        HOME_NOT_SET = getLanguage("language.HOME_NOT_SET", "&cYou must set home point first.");
        HOME_WELCOME = getLanguage("language.HOME_WELCOME", "&bWelcome home, guy.");
        HOME_BACK = getLanguage("language.HOME_BACK", "&bPay attention to your security.");
        HOME_BACK_NOT_SET = getLanguage("language.HOME_BACK_NOT_SET", "&cYou don't have a back point.");
        REPEAT = getLanguage("language.REPEAT", "&b[+1]&r");
        SKIN_HASH_NOT_SELECTED = getLanguage("language.SKIN_HASH_NOT_SELECTED", "&cYou must to specify a skin hash in User Center.");
        SPAWN_SET = getLanguage("language.SPAWN_SET", "&bSpawn point is set.");
    }
    
    private String getLanguage(String path, String def) {
        File file = new File(Main.plugin.getDataFolder(), "language/" + LANGUAGE + ".yml");
        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(file);
        return ColorUtil.replaceColor(Objects.requireNonNull(langConfig.getString(path, def)));
    }
}
