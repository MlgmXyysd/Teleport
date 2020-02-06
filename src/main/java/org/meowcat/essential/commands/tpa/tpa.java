package org.meowcat.essential.commands.tpa;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

import java.util.Objects;

import static org.bukkit.Bukkit.*;
import static org.meowcat.essential.utils.PlayerStatusUtil.getOfflineUUID;

public class tpa implements CommandExecutor {
    private static final String TYPE_TPA = "tpa";
    static final String TYPE_HERE = "here";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.TPA_TPA)) {
                if (args.length == 0) {
                    sender.sendMessage(LanguageUtil.PLAYER_NOT_SELECTED);
                } else {
                    check(sender, getPlayer(getOfflineUUID(args[0])), TYPE_TPA);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }

    static void check(CommandSender playerA, Player playerB, String type) {
        if (playerA.equals(playerB)) {
            playerA.sendMessage(LanguageUtil.TPA_YOUSELF);
        } else {
            if (getOnlinePlayers().contains(playerB)) {
                if (PlayerStatusUtil.canTeleport.containsKey(PlayerStatusUtil.getOfflineUUID(Objects.requireNonNull(playerB).getDisplayName()))) {
                    if (PlayerStatusUtil.canTeleport.get(PlayerStatusUtil.getOfflineUUID(Objects.requireNonNull(playerB).getDisplayName()))) {
                        send(playerA, playerB, type);
                    } else {
                        playerA.sendMessage(String.format(LanguageUtil.TPA_DISABLED, playerB.getName()));
                    }
                } else {
                    send(playerA, playerB, type);
                }
            } else {
                playerA.sendMessage(LanguageUtil.PLAYER_NOT_FOUND);
            }
        }
    }

    private static void send(CommandSender sender, Player playerB, String type) {
        Player playerA = (Player) sender;
        switch (type) {
            case TYPE_HERE:
                Objects.requireNonNull(playerB).sendMessage(String.format(LanguageUtil.TPA_REQUEST_HERE, playerA.getDisplayName()));
                playerB.playSound(playerB.getLocation(), Sound.valueOf(Main.plugin.getConfig().getString("configuration.sound-tpa-here", "ENTITY_PLAYER_LEVELUP")), 1.0F, 1.0F);
                PlayerStatusUtil.playerStatus.put(playerB.getUniqueId(), new PlayerStatusUtil.PlayerStatus(playerA.getUniqueId(), true));
                break;
            case TYPE_TPA:
                Objects.requireNonNull(playerB).sendMessage(String.format(LanguageUtil.TPA_REQUEST, playerA.getDisplayName()));
                playerB.playSound(playerB.getLocation(), Sound.valueOf(Main.plugin.getConfig().getString("configuration.sound-tpa", "ENTITY_PLAYER_LEVELUP")), 1.0F, 1.0F);
                PlayerStatusUtil.playerStatus.put(playerB.getUniqueId(), new PlayerStatusUtil.PlayerStatus(playerA.getUniqueId(), false));
                break;
            default:
                return;
        }
        Objects.requireNonNull(playerB).sendMessage(String.format(LanguageUtil.TPA_ACCEPT, "/tpaccept", "/tpyes", "/tpy"));
        Objects.requireNonNull(playerB).sendMessage(String.format(LanguageUtil.TPA_DENY, "/tpdeny", "/tpno", "/tpn"));
        Objects.requireNonNull(playerB).sendMessage(LanguageUtil.TPA_EXPIRE);
        playerA.sendMessage(String.format(LanguageUtil.TPA_SENDED, playerB.getDisplayName()));
        playerA.sendMessage(String.format(LanguageUtil.TPA_CANCEL, "/tpcancel " + playerB.getDisplayName()));
        playerA.sendMessage(LanguageUtil.TPA_EXPIRE);
        getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> PlayerStatusUtil.expireRequest(playerB.getUniqueId()), 1200);
    }
}
