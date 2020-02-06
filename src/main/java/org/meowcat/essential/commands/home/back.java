package org.meowcat.essential.commands.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.LocationUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class back implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.HOME_BACK)) {
                Player player = (Player) sender;
                if (LocationUtil.homeIsNull(player, "back")) {
                    player.sendMessage(LanguageUtil.HOME_BACK_NOT_SET);
                } else {
                    LocationUtil.teleportHome(player, "back");
                    player.sendMessage(LanguageUtil.HOME_BACK);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
