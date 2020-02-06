package org.meowcat.essential.commands.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.LocationUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class home implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.HOME_HOME)) {
                Player player = (Player) sender;
                if (LocationUtil.homeIsNull(player, "home")) {
                    player.sendMessage(LanguageUtil.HOME_NOT_SET);
                } else {
                    LocationUtil.teleportHome(player, "home");
                    player.sendMessage(LanguageUtil.HOME_WELCOME);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
