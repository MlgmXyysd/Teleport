package org.meowcat.essential.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class suicide implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.PERFORM_SUICIDE)) {
                Player player = (Player) sender;
                player.setHealth(0);
                sender.sendMessage(LanguageUtil.PERFORM_SUICIDE);
                Main.plugin.getServer().broadcastMessage(LanguageUtil.SUICIDE_BROADCAST);
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
