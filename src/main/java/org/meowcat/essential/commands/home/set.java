package org.meowcat.essential.commands.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.LocationUtil;
import org.meowcat.essential.utils.PermissionUtil;

import static org.meowcat.essential.utils.LocationUtil.TYPE_HOME;

public class set implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.HOME_SET)) {
                Player player = (Player) sender;
                player.setBedSpawnLocation(player.getLocation(), true);
                LocationUtil.setHome(player, TYPE_HOME);
                player.sendMessage(LanguageUtil.HOME_SET);
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
