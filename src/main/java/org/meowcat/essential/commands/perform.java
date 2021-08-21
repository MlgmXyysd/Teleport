package org.meowcat.essential.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

import static org.bukkit.Bukkit.getPlayer;
import static org.meowcat.essential.utils.PlayerStatusUtil.getOfflineUUID;

public class perform implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (PermissionUtil.hasPermission(sender, PermissionUtil.COMMAND_PERFORM)) {
            if (args.length == 0) {
                sender.sendMessage(LanguageUtil.PLAYER_NOT_SELECTED);
            } else {
                if (args.length != 1) {
                    Player player = getPlayer(getOfflineUUID(args[0]));
                    if (player != null && player.isOnline()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            if (i < 1) {
                                continue;
                            }
                            sb.append(args[i]);
                        }
                        player.performCommand(sb.toString());
                    } else {
                        sender.sendMessage(LanguageUtil.PLAYER_NOT_FOUND);
                    }
                }
            }
        }
        return true;
    }
}