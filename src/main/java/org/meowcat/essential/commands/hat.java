package org.meowcat.essential.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class hat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.HAT)) {
                Player player = (Player) sender;
                PlayerInventory playerInv = player.getInventory();
                ItemStack playerHand = playerInv.getItemInMainHand();
                ItemStack playerHelmet = playerInv.getHelmet();
                ItemStack air = new ItemStack(Material.AIR);
                if (playerHand.getType() == Material.AIR) {
                    if (playerHelmet == null) {
                        player.sendMessage(LanguageUtil.HAT_NOTHING);
                    } else {
                        if (playerHelmet.getType() == Material.AIR) {
                            player.sendMessage(LanguageUtil.HAT_NOTHING);
                        } else {
                            playerInv.setHelmet(air);
                            playerInv.setItemInMainHand(playerHelmet);
                            player.sendMessage(LanguageUtil.HAT_CLEAR);
                        }
                    }
                } else {
                    playerInv.setHelmet(playerHand);
                    playerInv.setItemInMainHand(playerHelmet);
                    player.sendMessage(LanguageUtil.HAT_ENJOY);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }
}
