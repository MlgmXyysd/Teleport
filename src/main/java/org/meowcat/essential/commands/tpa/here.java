package org.meowcat.essential.commands.tpa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

import static org.bukkit.Bukkit.*;
import static org.meowcat.essential.commands.tpa.tpa.*;

public class here implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.TPA_HERE)) {
                if (args.length == 0) {
                    sender.sendMessage(LanguageUtil.PLAYER_NOT_SELECTED);
                } else {
                    check(sender, getPlayer(PlayerStatusUtil.getOfflineUUID(args[0])), TYPE_HERE);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
