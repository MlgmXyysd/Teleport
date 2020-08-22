package org.meowcat.essential.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.ColorUtil;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class PlayerChatEvent implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        TextComponent message = new TextComponent(LanguageUtil.REPEAT);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, msg));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(LanguageUtil.CLICK_TO_REPEAT).create()));
        final Player player = event.getPlayer();
        List<Player> onlinePlayers = new ArrayList<>(getServer().getOnlinePlayers());
        if (msg.contains("@")) {
            if (msg.contains("@all")) {
                if (PermissionUtil.hasPermission(player, PermissionUtil.AT_ALL)) {
                    for (Player players : onlinePlayers) {
                        players.sendMessage(String.format(LanguageUtil.AT_ALL, player.getDisplayName()));
                        players.playSound(players.getLocation(), Sound.valueOf(Main.plugin.getConfig().getString("configuration.sound-at-all", "ENTITY_PLAYER_LEVELUP")), 1.0F, 1.0F);
                        msg = msg.replaceAll("@all", ChatColor.AQUA + "@all" + ChatColor.RESET);
                    }
                }
            } else {
                if (PermissionUtil.hasPermission(player, PermissionUtil.AT_PLAYER)) {
                    for (Player players : onlinePlayers) {
                        if (msg.toLowerCase().contains("@ " + players.getName().toLowerCase()) || msg.toLowerCase().contains("@" + players.getName().toLowerCase())) {
                            msg = msg.replaceAll("(?i)@ " + players.getName() + " ", ChatColor.AQUA + "@" + players.getName() + ChatColor.RESET + " ");
                            msg = msg.replaceAll("(?i)@ " + players.getName(), ChatColor.AQUA + "@" + players.getName() + ChatColor.RESET + " ");
                            msg = msg.replaceAll("(?i)@" + players.getName(), ChatColor.AQUA + "@" + players.getName() + ChatColor.RESET + " ");
                            if (PlayerStatusUtil.canCall.containsKey(PlayerStatusUtil.getOfflineUUID(players.getDisplayName()))) {
                                if (PlayerStatusUtil.canCall.get(PlayerStatusUtil.getOfflineUUID(players.getDisplayName()))) {
                                    players.sendMessage(String.format(LanguageUtil.AT_PLAYER, player.getDisplayName()));
                                    players.playSound(players.getLocation(), Sound.valueOf(Main.plugin.getConfig().getString("configuration.sound-at", "ENTITY_PLAYER_LEVELUP")), 1.0F, 1.0F);
                                } else {
                                    player.sendMessage(String.format(LanguageUtil.AT_DISABLED, players.getName()));
                                }
                            } else {
                                players.sendMessage(String.format(LanguageUtil.AT_PLAYER, player.getDisplayName()));
                                players.playSound(players.getLocation(), Sound.valueOf(Main.plugin.getConfig().getString("configuration.sound-at", "ENTITY_PLAYER_LEVELUP")), 1.0F, 1.0F);
                            }
                        }
                    }
                }
            }
        }
        if (PermissionUtil.hasPermission(player, PermissionUtil.COLOR_CHAT)) {
            msg = ColorUtil.replaceColor(msg);
        }
        event.setMessage(msg);
        for (Player players : onlinePlayers) {
            if (PlayerStatusUtil.canRepeat.containsKey(PlayerStatusUtil.getOfflineUUID(players.getDisplayName()))) {
                if (PlayerStatusUtil.canRepeat.get(PlayerStatusUtil.getOfflineUUID(players.getDisplayName()))) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            players.spigot().sendMessage(message);
                        }
                    }.runTaskLaterAsynchronously(Main.plugin, 1);
                }
            }
        }
    }
}
