package org.meowcat.essential.utils;

import com.google.common.base.Charsets;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPlayer;

public class PlayerStatusUtil implements TabCompleter {
    public static Map<UUID, PlayerStatus> playerStatus = new HashMap<>();
    public static Map<UUID, Boolean> canCall = new HashMap<>();
    public static Map<UUID, Boolean> canTeleport = new HashMap<>();
    public static Map<UUID, Boolean> canRepeat = new HashMap<>();

    public static void expireRequest(UUID key) {
        if (playerStatus.containsKey(key)) {
            Player sender = getPlayer(playerStatus.get(key).getTargetPlayer());
            if (getOnlinePlayers().contains(getPlayer(key))) {
                Objects.requireNonNull(getPlayer(key)).sendMessage(ChatColor.AQUA + LanguageUtil.PENDING_EXPIRED);
            }
            if (getOnlinePlayers().contains(sender)) {
                Objects.requireNonNull(sender).sendMessage(ChatColor.AQUA + LanguageUtil.PENDING_EXPIRED);
            }
            playerStatus.remove(key);
        }
    }

    public static UUID getOfflineUUID(String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Collection<? extends Player> playerCollection = getOnlinePlayers();
        List<String> playerList = new ArrayList<>();
        for (Player o : playerCollection) {
            playerList.add((o).getDisplayName());
        }
        if (args.length > 1) {
            return new ArrayList<>();
        }
        if (args.length == 0) {
            return playerList;
        }
        String[] players = new String[playerList.size()];
        playerList.toArray(players);
        return Arrays.stream(players).filter(s -> s.toLowerCase().startsWith(args[0])).collect(Collectors.toList());
    }

    @Data
    public static class PlayerStatus {
        UUID targetPlayer;
        boolean isTpHere;

        public PlayerStatus(UUID player, boolean tpHere) {
            targetPlayer = player;
            isTpHere = tpHere;
        }
    }
}
