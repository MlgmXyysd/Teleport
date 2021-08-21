package org.meowcat.essential.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

import java.util.Collection;

public class all implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.TELEPORT_ALL, true)) {
                if (args.length != 0 && args[0].equals("confirm")) {
                    Location location = ((Player) sender).getLocation();
                    Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                    for (Player player : onlinePlayers) {
                        if (player.isOnline()) {
                            player.teleport(location);
                        }
                    }
                } else {
                    sender.sendMessage(LanguageUtil.TPALL_CONFIRM);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
