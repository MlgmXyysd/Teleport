package org.meowcat.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("deprecation")
public final class Main extends JavaPlugin {

    private Map<String, PlayerStatus> playerStatus = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("[Teleport] Teleport by MeowCat Studio");
        Bukkit.getConsoleSender().sendMessage("[Teleport] Official Website http://www.meowcat.org/");
        Bukkit.getConsoleSender().sendMessage("[Teleport] Plugin enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[Teleport] Teleport by MeowCat Studio");
        Bukkit.getConsoleSender().sendMessage("[Teleport] Official Website http://www.meowcat.org/");
        Bukkit.getConsoleSender().sendMessage("[Teleport] Plugin disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        PlayerStatus status;
        switch (label) {
            case "tpa":
                if (sender instanceof Player) {
                    if (args.length == 0) {
                        sender.sendMessage("\u00a7cYou must select a player to teleport.");
                    } else {
                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                            Player playerA = (Player) sender;
                            Player playerB = Bukkit.getPlayer(args[0]);
                            Objects.requireNonNull(playerB).sendMessage("\u00a76Player \u00a7c" + playerA.getDisplayName() + "\u00a76 has requested to teleport to you.");
                            Objects.requireNonNull(playerB).sendMessage("\u00a76To accept this request, type \u00a7c/tpaccept\u00a76 or \u00a7c/tpyes\u00a76.");
                            Objects.requireNonNull(playerB).sendMessage("\u00a76To deny this request, type \u00a7c/tpdeny\u00a76 or \u00a7c/tpno\u00a76.");
                            playerA.sendMessage("\u00a76A teleport request is sent to \u00a7c" + playerB.getDisplayName() + "\u00a76.");
                            playerA.sendMessage("\u00a76To cancel this request, type \u00a7c/tpcancel\u00a76.");
                            status = new PlayerStatus(playerA, playerB, System.currentTimeMillis() + 60000, true);
                            playerStatus.put(playerB.getDisplayName(), status);
                        } else {
                            sender.sendMessage("\u00a7cPlayer not online or not found.");
                        }
                    }
                } else {
                    sender.sendMessage("\u00a7cAn error has occurred, so you can't send teleport request.");
                }
                break;
            case "tpaccept":
            case "tpyes":
                if (playerStatus.containsKey(sender.getName())) {
                    status = playerStatus.get(sender.getName());
                    if (status.isSend()) {
                        if (status.getExpiredTime() < System.currentTimeMillis()) {
                            if (status.getTpSender().isOnline()) {
                                status.getTpSender().teleport(((Player) sender).getLocation());
                                status.setSend(false);
                                status.getTpSender().sendMessage("\u00a7c" + ((Player) sender).getDisplayName() + "\u00a76 accepted your teleport request.");
                                sender.sendMessage("\u00a76Teleport request accepted.");
                            } else {
                                sender.sendMessage("\u00a7cPlayer not online or not found.");
                            }
                        } else {
                            status.setSend(false);
                            sender.sendMessage("\u00a7cTeleport request expired.");
                        }
                    } else {
                        sendNotPending(sender);
                    }
                } else {
                    sendNotPending(sender);
                }
                break;
            case "tpdeny":
            case "tpno":
                if (playerStatus.containsKey(sender.getName())) {
                    status = playerStatus.get(sender.getName());
                    if (status.isSend()) {
                        if (status.getExpiredTime() < System.currentTimeMillis()) {
                            status.getTpSender().sendMessage("\u00a7c" + ((Player) sender).getDisplayName() + "\u00a76 denied your teleport request.");
                            status.setSend(false);
                            sender.sendMessage("\u00a76Teleport request denied.");
                        } else {
                            status.setSend(false);
                            sender.sendMessage("\u00a7cTeleport request expired.");
                        }
                    } else {
                        sendNotPending(sender);
                    }
                } else {
                    sendNotPending(sender);
                }
                break;
            case "tpcancel":
                if (playerStatus.containsKey(sender.getName())) {
                    status = playerStatus.get(sender.getName());
                    if (status.isSend()) {
                        if (status.getExpiredTime() < System.currentTimeMillis()) {
                            status.setSend(false);
                            if (status.getTpReceiver().isOnline()) {
                                status.getTpReceiver().sendMessage("\u00a76Teleport request canceled by sender.");
                            }
                            sender.sendMessage("\u00a76Teleport request canceled.");
                        } else {
                            status.setSend(false);
                            sender.sendMessage("\u00a7cTeleport request expired.");
                        }
                    } else {
                        sendNotPending(sender);
                    }
                } else {
                    sendNotPending(sender);
                }
                break;
        }
        return true;
    }

    private void sendNotPending(CommandSender sender) {
        sender.sendMessage("\u00a7cYou do not have a pending request.");
    }

//    @Override
//    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
//        //如果不是能够补全的长度，则返回空列表
//        if (args.length > 1) return new ArrayList<>();
//
//        //如果此时仅输入了命令"sub"，则直接返回所有的子命令
//        if (args.length == 0) return Arrays.asList(subCommands);
//
//        //筛选所有可能的补全列表，并返回
//        return Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
//    }
}
