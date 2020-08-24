package org.meowcat.essential.commands.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class spawnpoint implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.HOME_SPAWN)) {
                Player player = (Player) sender;
                player.setBedSpawnLocation(player.getLocation(), true);
                player.sendMessage(LanguageUtil.SPAWN_SET);
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
