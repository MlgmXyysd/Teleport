package org.meowcat.essential.commands.whitelist;

import com.mojang.authlib.GameProfile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.WhiteListUtil;

import static org.meowcat.essential.utils.PlayerStatusUtil.getOfflineUUID;

public class add implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (PermissionUtil.hasPermission(sender, PermissionUtil.WHITELIST_ADD)) {
            if (args.length == 0) {
                sender.sendMessage(LanguageUtil.PLAYER_NOT_SELECTED);
            } else {
                WhiteListUtil.invokeMethod(WhiteListUtil.add, WhiteListUtil.invokeMethod(WhiteListUtil.whitelist, WhiteListUtil.getNMSPlayerList(sender.getServer())), WhiteListUtil.newInstance(WhiteListUtil.whiteListEntry, GameProfile.class, new GameProfile(getOfflineUUID(args[0]), args[0])));
                sender.sendMessage(String.format(LanguageUtil.WHITELIST_ADDED, args[0]));
            }
        }
        return true;
    }
}
