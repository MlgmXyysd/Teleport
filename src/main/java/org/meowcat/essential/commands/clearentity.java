package org.meowcat.essential.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class clearentity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (PermissionUtil.hasPermission(sender, PermissionUtil.CLEARENTITY)) {
            Main.killEntity();
            sender.sendMessage(LanguageUtil.CLEARENTITY_COMPLETE);
        }
        return true;
    }
}
