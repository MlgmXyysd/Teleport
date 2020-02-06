package org.meowcat.essential.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void playerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!PlayerStatusUtil.canTeleport.containsKey(PlayerStatusUtil.getOfflineUUID(player.getDisplayName()))) {
            PlayerStatusUtil.canTeleport.put(PlayerStatusUtil.getOfflineUUID(player.getDisplayName()), Main.plugin.getConfig().getBoolean("configuration.default-section.teleport", true));
        }
        if (!PlayerStatusUtil.canCall.containsKey(PlayerStatusUtil.getOfflineUUID(player.getDisplayName()))) {
            PlayerStatusUtil.canCall.put(PlayerStatusUtil.getOfflineUUID(player.getDisplayName()), Main.plugin.getConfig().getBoolean("configuration.default-section.call", true));
        }
        if (!PlayerStatusUtil.canRepeat.containsKey(PlayerStatusUtil.getOfflineUUID(player.getDisplayName()))) {
            PlayerStatusUtil.canRepeat.put(PlayerStatusUtil.getOfflineUUID(player.getDisplayName()), Main.plugin.getConfig().getBoolean("configuration.default-section.repeater", true));
        }
        if (Main.plugin.getConfig().getBoolean("configuration.update-message", true)) {
            if (PermissionUtil.hasPermission(player, PermissionUtil.UPDATE)) {
                if (Main.plugin.getConfig().getBoolean("configuration.update-check", true)) {
                    if (!Main.isLatest) {
                        player.sendMessage(ChatColor.GOLD + "[MeowEssential] " + LanguageUtil.NOT_LATEST);
                    }
                } else {
                    player.sendMessage(ChatColor.GOLD + "[MeowEssential] " + LanguageUtil.UPDATE_DISABLED);
                }
            }
        }
    }
}
