package org.meowcat.essential.commands.skin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

import static org.bukkit.Bukkit.getConsoleSender;

public class Change implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.SKIN_CHANGE)) {
                if (args.length == 0) {
                    sender.sendMessage(LanguageUtil.SKIN_HASH_NOT_SELECTED);
                } else {
                    Bukkit.getServer().dispatchCommand(getConsoleSender(), "skin set " + sender.getName() + " " + Main.plugin.getConfig().getString("configuration.skin-root", "http://localhost/textures/") + args[0]);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
