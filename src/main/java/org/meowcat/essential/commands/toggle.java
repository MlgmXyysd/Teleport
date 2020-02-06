package org.meowcat.essential.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class toggle implements TabExecutor {
    private final String[] tabComplete = {"at", "tp", "repeat"};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "at":
                        if (PermissionUtil.hasPermission(sender, PermissionUtil.TOGGLE_AT)) {
                            if (PlayerStatusUtil.canCall.containsKey(PlayerStatusUtil.getOfflineUUID(sender.getName()))) {
                                if (PlayerStatusUtil.canCall.get(PlayerStatusUtil.getOfflineUUID(sender.getName()))) {
                                    sender.sendMessage(LanguageUtil.TOGGLE_AT_DISABLE);
                                    PlayerStatusUtil.canCall.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), false);
                                } else {
                                    sender.sendMessage(LanguageUtil.TOGGLE_AT_ENABLE);
                                    PlayerStatusUtil.canCall.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), true);
                                }
                            } else {
                                sender.sendMessage(LanguageUtil.TOGGLE_AT_ENABLE);
                                PlayerStatusUtil.canCall.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), true);
                            }
                        }
                        break;
                    case "tp":
                        if (PermissionUtil.hasPermission(sender, PermissionUtil.TOGGLE_TP)) {
                            if (PlayerStatusUtil.canTeleport.containsKey(PlayerStatusUtil.getOfflineUUID(sender.getName()))) {
                                if (PlayerStatusUtil.canTeleport.get(PlayerStatusUtil.getOfflineUUID(sender.getName()))) {
                                    sender.sendMessage(LanguageUtil.TOGGLE_TP_DISABLE);
                                    PlayerStatusUtil.canTeleport.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), false);
                                } else {
                                    sender.sendMessage(LanguageUtil.TOGGLE_TP_ENABLE);
                                    PlayerStatusUtil.canTeleport.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), true);
                                }
                            } else {
                                sender.sendMessage(LanguageUtil.TOGGLE_TP_ENABLE);
                                PlayerStatusUtil.canTeleport.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), true);
                            }
                        }
                        break;
                    case "repeat":
                        if (PermissionUtil.hasPermission(sender, PermissionUtil.TOGGLE_PO)) {
                            if (PlayerStatusUtil.canRepeat.containsKey(PlayerStatusUtil.getOfflineUUID(sender.getName()))) {
                                if (PlayerStatusUtil.canRepeat.get(PlayerStatusUtil.getOfflineUUID(sender.getName()))) {
                                    sender.sendMessage(LanguageUtil.TOGGLE_PO_DISABLE);
                                    PlayerStatusUtil.canRepeat.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), false);
                                } else {
                                    sender.sendMessage(LanguageUtil.TOGGLE_PO_ENABLE);
                                    PlayerStatusUtil.canRepeat.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), true);
                                }
                            } else {
                                sender.sendMessage(LanguageUtil.TOGGLE_PO_ENABLE);
                                PlayerStatusUtil.canRepeat.put(PlayerStatusUtil.getOfflineUUID(sender.getName()), true);
                            }
                        }
                        break;
                    default:
                        sender.sendMessage(LanguageUtil.ACTION_NOT_FOUND);
                }
            } else {
                sender.sendMessage(LanguageUtil.ACTION_NOT_FOUND);
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) {
            return new ArrayList<>();
        }
        if (args.length == 0) {
            return Arrays.asList(tabComplete);
        }
        return Arrays.stream(tabComplete).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}
