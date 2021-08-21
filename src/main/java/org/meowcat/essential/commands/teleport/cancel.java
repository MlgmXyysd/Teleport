package org.meowcat.essential.commands.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

import java.util.Objects;

import static org.bukkit.Bukkit.getPlayer;
import static org.meowcat.essential.utils.PlayerStatusUtil.getOfflineUUID;

public class cancel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.TELEPORT_CANCEL)) {
                if (args.length == 0) {
                    sender.sendMessage(LanguageUtil.PLAYER_NOT_SELECTED);
                } else {
                    Player player = getPlayer(getOfflineUUID(args[0]));
                    if (PlayerStatusUtil.playerStatus.containsKey(getOfflineUUID(args[0]))) {
                        Player status = getPlayer(PlayerStatusUtil.playerStatus.get(getOfflineUUID(args[0])).getTargetPlayer());
                        if (Objects.requireNonNull(status).getUniqueId().equals(((Player) sender).getUniqueId())) {
                            Objects.requireNonNull(player).sendMessage(LanguageUtil.TPA_CANCELED_RECEIVER);
                            sender.sendMessage(LanguageUtil.TPA_CANCELED_SENDER);
                            PlayerStatusUtil.playerStatus.remove(player.getUniqueId());
                        } else {
                            sender.sendMessage(LanguageUtil.TPA_CANCEL_NONE);
                        }
                    } else {
                        sender.sendMessage(LanguageUtil.TPA_CANCEL_NONE);
                    }
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
