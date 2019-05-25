package org.meowcat.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@SuppressWarnings("deprecation")
public final class Main extends JavaPlugin {

    private static Boolean isSend;
    private static Player tpSender;
    private static Player tpReceiver;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("Teleport by MeowCat Studio");
        Bukkit.getConsoleSender().sendMessage("Official Website http://www.meowcat.org/");
        Bukkit.getConsoleSender().sendMessage("[Teleport] Plugin enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Teleport by MeowCat Studio");
        Bukkit.getConsoleSender().sendMessage("Official Website http://www.meowcat.org/");
        Bukkit.getConsoleSender().sendMessage("[Teleport] Plugin disabled.");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (label) {
            case "tpa":
                if (sender instanceof Player) {
                    Player playerA = (Player) sender;
                    tpSender = playerA;
                    if (args.length == 0) {
                        playerA.sendMessage("\u00a7cYou must select a player to teleport.");
                    } else {
                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                            Player playerB = Bukkit.getPlayer(args[0]);
                            tpReceiver = playerB;
                            Objects.requireNonNull(playerB).sendMessage("\u00a76Player \u00a7c" + playerA + "\u00a76 has requested to teleport to you.");
                            Objects.requireNonNull(playerB).sendMessage("\u00a76To accept this request, type \u00a7c/tpaccept\u00a76 or \u00a7c/tpyes\u00a76.");
                            Objects.requireNonNull(playerB).sendMessage("\u00a76To deny this request, type \u00a7c/tpdeny\u00a76 or \u00a7c/tpno\u00a76.");
                            playerA.sendMessage("\u00a76A teleport request is sent to \u00a7c" + playerB + "\u00a76.");
                            playerA.sendMessage("\u00a76To cancel this request, type \u00a7c/tpcancel\u00a76.");
                            isSend = true;
                        } else {
                            playerA.sendMessage("\u00a7cPlayer not online or not found.");
                        }
                    }
                } else {
                    sender.sendMessage("\u00a7cAn error has occurred, so you can't send teleport request.");
                }
                break;
            case "tpaccept":
            case "tpyes":
                if (isSend) {
                    if (tpSender.isOnline()) {
                        tpSender.teleport(((Player)sender).getLocation());
                        isSend = false;
                        tpSender.sendMessage("\u00a7c" + sender + "\u00a76 accepted your teleport request.");
                        sender.sendMessage("\u00a76Teleport request accepted.");
                    } else {
                        sender.sendMessage("\u00a7cPlayer not online or not found.");
                    }
                } else {
                    sender.sendMessage("\u00a7cYou do not have a pending request.");
                }
                break;
            case "tpdeny":
            case "tpno":
                if (isSend) {
                    tpSender.sendMessage("\u00a7c" + sender + "\u00a76 denied your teleport request.");
                    sender.sendMessage("\u00a76Teleport request denied.");
                } else {
                    sender.sendMessage("\u00a7cYou do not have a pending request.");
                }
                break;
            case "tpcancel":
                if (isSend) {
                    isSend = false;
                    if (tpReceiver.isOnline()) {
                        tpReceiver.sendMessage("\u00a76Teleport request canceled by sender.");
                    }
                    sender.sendMessage("\u00a76Teleport request canceled.");
                } else {
                    sender.sendMessage("\u00a7cYou do not have a pending request.");
                }
                break;
        }
        return true;
    }
}
