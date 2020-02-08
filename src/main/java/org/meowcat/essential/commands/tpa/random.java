package org.meowcat.essential.commands.tpa;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.meowcat.essential.Main;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.PermissionUtil;

import java.util.*;

public class random implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (PermissionUtil.hasPermission(sender, PermissionUtil.TPA_RANDOM)) {
                Player player = (Player) sender;
                World world = player.getLocation().getWorld();
                if (!Main.plugin.getConfig().getStringList("configuration.teleport-random.world-list").contains(Objects.requireNonNull(world).getName())) {
                    sender.sendMessage(LanguageUtil.TPA_RANDOM_NOT_ALLOWED);
                } else {
                    int playerX = player.getLocation().getBlockX();
                    int playerY = player.getLocation().getBlockY();
                    int playerZ = player.getLocation().getBlockZ();
                    playerX += getRandomInt(Main.plugin.getConfig().getInt("configuration.teleport-random.X-min", -500), Main.plugin.getConfig().getInt("configuration.teleport-random.X-max", 500));
                    playerZ += getRandomInt(Main.plugin.getConfig().getInt("configuration.teleport-random.Z-min", -500), Main.plugin.getConfig().getInt("configuration.teleport-random.Z-max", 500));
                    while (true) {
                        if (world.getBlockAt(playerX, playerY, playerZ).isEmpty() && world.getBlockAt(playerX, playerY + 1, playerZ).isEmpty()) {
                            break;
                        } else {
                            playerY++;
                        }
                    }
                    player.teleport(new Location(world, playerX, playerY, playerZ, player.getLocation().getYaw(), player.getLocation().getPitch()));
                    player.sendMessage(LanguageUtil.HOME_BACK);
                }
            }
        } else {
            sender.sendMessage(LanguageUtil.PLAYER_ONLY);
        }
        return true;
    }

    private int getRandomInt(int min, int max) {
        int result;
        Random random = new Random();
        if (min > max) {
            result = 0;
        } else if (max < 0) {
            result = -(random.nextInt(1 + max - min) - max);
        } else {
            result = random.nextInt(1 + max - min) + min;
        }
        return result;
    }
}

