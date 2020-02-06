package org.meowcat.essential.commands.tpa;

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

public class deny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (PermissionUtil.hasPermission(sender, PermissionUtil.TPA_DENY)) {
            if (PlayerStatusUtil.playerStatus.containsKey(getOfflineUUID(sender.getName()))) {
                Player status = getPlayer(PlayerStatusUtil.playerStatus.get(getOfflineUUID(sender.getName())).getTargetPlayer());
                if (Objects.requireNonNull(status).isOnline()) {
                    status.sendMessage(String.format(LanguageUtil.TPDENY_SENDER, sender.getName()));
                    sender.sendMessage(LanguageUtil.TPDENY_RECEIVER);
                } else {
                    sender.sendMessage(LanguageUtil.PLAYER_NOT_FOUND);
                }
                PlayerStatusUtil.playerStatus.remove(((Player) sender).getUniqueId());
            } else {
                sender.sendMessage(LanguageUtil.NOT_PENDING_REQUEST);
            }
        }
        return true;
    }
}
