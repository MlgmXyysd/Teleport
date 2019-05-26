package org.meowcat.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"deprecation", "SuspiciousMethodCalls"})
public final class Main extends JavaPlugin {

    private Map<Player, Player> playerStatus = new HashMap<>();

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
        Player status;
        switch (label) {
            case "tpa":
                if (sender instanceof Player) {
                    if (args.length == 0) {
                        sender.sendMessage("§cYou must select a player to teleport.");
                    } else {
                        Player playerA = (Player) sender;
                        Player playerB = Bukkit.getPlayer(args[0]);
                        if (playerA.equals(playerB)) {
                            sender.sendMessage("§cYou can't teleport to yourself.");
                        } else {
                            if (Bukkit.getOnlinePlayers().contains(playerB)) {
                                Objects.requireNonNull(playerB).sendMessage("§6Player §c" + playerA.getDisplayName() + "§6 has requested to teleport to you.");
                                Objects.requireNonNull(playerB).sendMessage("§6To accept this request, type §c/tpaccept§6 or §c/tpyes§6.");
                                Objects.requireNonNull(playerB).sendMessage("§6To deny this request, type §c/tpdeny§6 or §c/tpno§6.");
                                playerA.sendMessage("§6A teleport request is sent to §c" + playerB.getDisplayName() + "§6.");
                                playerA.sendMessage("§6To cancel this request, type §c/tpcancel§6.");
                                playerStatus.put(playerB, playerA);
                                getServer().getScheduler().scheduleSyncDelayedTask(this, () -> expireRequest(playerB), 60000);
                            } else {
                                sender.sendMessage("§cPlayer not online or not found.");
                            }
                        }
                    }
                } else {
                    sender.sendMessage("§cYou only can teleport as a player.");
                }
                break;
            case "tpaccept":
            case "tpyes":
                if (playerStatus.containsKey(sender)) {
                    status = playerStatus.get(sender);
                    if (status.isOnline()) {
                        status.teleport(((Player) sender).getLocation());
                        status.sendMessage("§c" + ((Player) sender).getDisplayName() + "§6 accepted your teleport request.");
                        sender.sendMessage("§6Teleport request accepted.");
                    } else {
                        sender.sendMessage("§cPlayer not online or not found.");
                    }
                    playerStatus.remove(sender);
                } else {
                    sender.sendMessage("§cYou do not have a pending request.");
                }
                break;
            case "tpdeny":
            case "tpno":
                if (playerStatus.containsKey(sender)) {
                    status = playerStatus.get(sender);
                    if (status.isOnline()) {
                        status.sendMessage("§c" + ((Player) sender).getDisplayName() + "§6 denied your teleport request.");
                        sender.sendMessage("§6Teleport request denied.");
                    } else {
                        sender.sendMessage("§cPlayer not online or not found.");
                    }
                    playerStatus.remove(sender);
                } else {
                    sender.sendMessage("§cYou do not have a pending request.");
                }
                break;
            case "tpcancel":
                if (sender instanceof Player) {
                    if (args.length == 0) {
                        sender.sendMessage("§cYou must select a player to cancel teleport request.");
                    } else {
                        Player player = Bukkit.getPlayer(args[0]);
                        if (playerStatus.containsKey(player)) {
                            status = playerStatus.get(player);
                            if (status.equals(player)) {
                                player.sendMessage("§6Teleport request canceled by sender.");
                                sender.sendMessage("§6Teleport request canceled.");
                                playerStatus.remove(player);
                            } else {
                                sender.sendMessage("§cYou do not have a pending request for this player.");
                            }
                        } else {
                            sender.sendMessage("§cYou do not have a pending request for this player.");
                        }
                    }
                } else {
                    sender.sendMessage("§cYou only can cancel teleport request as a player.");
                }
                break;
            case "hat":
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PlayerInventory playerInv = player.getInventory();
                    ItemStack playerHand = player.getItemInHand();
                    ItemStack playerHelmet = playerInv.getHelmet();
                    ItemStack air = new ItemStack(Material.AIR);
                    if (playerHand.getType() == Material.AIR) {
                        if (playerHelmet == null) {
                            player.sendMessage("§cThere's nothing on your head or hand..");
                        } else {
                            if (playerHelmet.getType() == Material.AIR) {
                                player.sendMessage("§cThere's nothing on your head or hand..");
                            } else {
                                playerInv.setHelmet(air);
                                player.setItemInHand(playerHelmet);
                                player.updateInventory();
                                player.sendMessage("§6Your hat has been removed.");
                            }
                        }
                    } else {
                        playerInv.setHelmet(playerHand);
                        player.setItemInHand(playerHelmet);
                        player.updateInventory();
                        player.sendMessage("§6Enjoy your new hat.");
                    }
                } else {
                    sender.sendMessage("§cYou only can put item on your head as a player.");
                }
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Collection playerCollection = Bukkit.getOnlinePlayers();
        List<String> playerList = new ArrayList<>();
        for (Object o : playerCollection) {
            playerList.add(((Player) o).getDisplayName());
        }
        if (args.length > 1) {
            return new ArrayList<>();
        }
        if (args.length == 0) {
            return playerList;
        }
        String[] players = new String[playerList.size()];
        playerList.toArray(players);
        return Arrays.stream(players).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }

    private void expireRequest(Player key) {
        if (playerStatus.containsKey(key)) {
            Player sender = playerStatus.get(key);
            if (key != null) {
                if (Bukkit.getOnlinePlayers().contains(key)) {
                    key.sendMessage("§cTeleport request expired.");
                }
            }
            if (sender != null) {
                if (Bukkit.getOnlinePlayers().contains(sender)) {
                    sender.sendMessage("§cTeleport request expired.");
                }
            }
            playerStatus.remove(key);
        }
    }
}
