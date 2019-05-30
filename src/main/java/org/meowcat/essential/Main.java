package org.meowcat.essential;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.meowcat.essential.utils.NMSUtil;

import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getConsoleSender;

public final class Main extends JavaPlugin implements Listener {

    private Map<UUID, UUID> playerStatus = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getConsoleSender().sendMessage("[MeowEssential] MeowEssential by MeowCat Studio");
        getConsoleSender().sendMessage("[MeowEssential] Official Website http://www.meowcat.org/");
        getConsoleSender().sendMessage("[MeowEssential] Plugin enabled.");
        getServer().getScheduler().scheduleSyncDelayedTask(this, this::killEntity, 6000);
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage("[MeowEssential] MeowEssential by MeowCat Studio");
        getConsoleSender().sendMessage("[MeowEssential] Official Website http://www.meowcat.org/");
        getConsoleSender().sendMessage("[MeowEssential] Plugin disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player status;
        switch (label.toLowerCase()) {
            case "tpa":
                if (sender instanceof Player) {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.RED + "You must select a player to teleport.");
                    } else {
                        Player playerA = (Player) sender;
                        Player playerB = Bukkit.getPlayer(getOfflineUUID(args[0]));
                        if (playerA.equals(playerB)) {
                            sender.sendMessage(ChatColor.RED + "You can't teleport to yourself.");
                        } else {
                            if (Bukkit.getOnlinePlayers().contains(playerB)) {
                                Objects.requireNonNull(playerB).sendMessage(ChatColor.AQUA + "Player " + ChatColor.GREEN + playerA.getDisplayName() + ChatColor.AQUA + " has requested to teleport to you.");
                                Objects.requireNonNull(playerB).sendMessage(ChatColor.AQUA + "To accept this request, type " + ChatColor.RED + "/tpaccept" + ChatColor.AQUA + " or " + ChatColor.RED + "/tpyes" + ChatColor.AQUA + ".");
                                Objects.requireNonNull(playerB).sendMessage(ChatColor.AQUA + "To deny this request, type " + ChatColor.RED + "/tpdeny" + ChatColor.AQUA + " or " + ChatColor.RED + "/tpno" + ChatColor.AQUA + ".");
                                Objects.requireNonNull(playerB).sendMessage(ChatColor.AQUA + "Request will expired in 60 seconds.");
                                playerA.sendMessage(ChatColor.AQUA + "A teleport request is sent to " + ChatColor.GREEN + playerB.getDisplayName() + ChatColor.AQUA + ".");
                                playerA.sendMessage(ChatColor.AQUA + "To cancel this request, type " + ChatColor.RED + "/tpcancel " + playerB.getDisplayName() + ChatColor.AQUA + ".");
                                playerA.sendMessage(ChatColor.AQUA + "Request will expired in 60 seconds.");
                                playerStatus.put(playerB.getUniqueId(), playerA.getUniqueId());
                                getServer().getScheduler().scheduleSyncDelayedTask(this, () -> expireRequest(playerB.getUniqueId()), 1200);
                            } else {
                                sender.sendMessage(ChatColor.RED + "Player not online or not found.");
                            }
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You only can teleport as a player.");
                }
                break;
            case "tpaccept":
            case "tpyes":
                if (playerStatus.containsKey(getOfflineUUID(sender.getName()))) {
                    status = Bukkit.getPlayer(playerStatus.get(getOfflineUUID(sender.getName())));
                    if (Objects.requireNonNull(status).isOnline()) {
                        status.teleport(((Player) sender).getLocation());
                        status.sendMessage(ChatColor.GREEN + ((Player) sender).getDisplayName() + ChatColor.AQUA + " accepted your teleport request.");
                        sender.sendMessage(ChatColor.AQUA + "Teleport request accepted.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not online or not found.");
                    }
                    playerStatus.remove(((Player) sender).getUniqueId());
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have a pending request.");
                }
                break;
            case "tpdeny":
            case "tpno":
                if (playerStatus.containsKey(getOfflineUUID(sender.getName()))) {
                    status = Bukkit.getPlayer(playerStatus.get(getOfflineUUID(sender.getName())));
                    if (Objects.requireNonNull(status).isOnline()) {
                        status.sendMessage(ChatColor.GREEN + ((Player) sender).getDisplayName() + ChatColor.AQUA + " denied your teleport request.");
                        sender.sendMessage(ChatColor.AQUA + "Teleport request denied.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not online or not found.");
                    }
                    playerStatus.remove(((Player) sender).getUniqueId());
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have a pending request.");
                }
                break;
            case "tpcancel":
                if (sender instanceof Player) {
                    if (args.length == 0) {
                        sender.sendMessage(ChatColor.RED + "You must select a player to cancel teleport request.");
                    } else {
                        Player player = Bukkit.getPlayer(getOfflineUUID(args[0]));
                        if (playerStatus.containsKey(getOfflineUUID(args[0]))) {
                            status = Bukkit.getPlayer(playerStatus.get(getOfflineUUID(args[0])));
                            if (Objects.requireNonNull(status).getUniqueId().equals(((Player) sender).getUniqueId())) {
                                Objects.requireNonNull(player).sendMessage(ChatColor.AQUA + "Teleport request canceled by sender.");
                                sender.sendMessage(ChatColor.AQUA + "Teleport request canceled.");
                                playerStatus.remove(player.getUniqueId());
                            } else {
                                sender.sendMessage(ChatColor.RED + "You do not have a pending request for this player.");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "This player do not have any pending requests.");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You only can cancel teleport request as a player.");
                }
                break;
            case "hat":
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    PlayerInventory playerInv = player.getInventory();
                    ItemStack playerHand = playerInv.getItemInMainHand();
                    ItemStack playerHelmet = playerInv.getHelmet();
                    ItemStack air = new ItemStack(Material.AIR);
                    if (playerHand.getType() == Material.AIR) {
                        if (playerHelmet == null) {
                            player.sendMessage(ChatColor.RED + "There's nothing on your head or hand..");
                        } else {
                            if (playerHelmet.getType() == Material.AIR) {
                                player.sendMessage(ChatColor.RED + "There's nothing on your head or hand..");
                            } else {
                                playerInv.setHelmet(air);
                                playerInv.setItemInMainHand(playerHelmet);
                                player.sendMessage(ChatColor.AQUA + "Your hat has been removed.");
                            }
                        }
                    } else {
                        playerInv.setHelmet(playerHand);
                        playerInv.setItemInMainHand(playerHelmet);
                        player.sendMessage(ChatColor.AQUA + "Enjoy your new hat.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You only can put item on your head as a player.");
                }
                break;
            case "wladd":
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "You must select a player to add.");
                } else {
                    NMSUtil.invokeMethod(NMSUtil.add, NMSUtil.invokeMethod(NMSUtil.whitelist, NMSUtil.getNMSPlayerList(sender.getServer())), NMSUtil.newInstance(NMSUtil.whiteListEntry, GameProfile.class, new GameProfile(getOfflineUUID(args[0]), args[0])));
                    sender.sendMessage(ChatColor.AQUA + "Added " + ChatColor.RED + args[0] + ChatColor.AQUA + " to whitelist with offline UUID.");
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
        return Arrays.stream(players).filter(s -> s.toLowerCase().startsWith(args[0])).collect(Collectors.toList());
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        final Player player = event.getPlayer();
        if (msg.contains("@")) {
            List<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
            if (msg.contains("@all")) {
                for (Player players : onlinePlayers) {
                    players.sendMessage(ChatColor.AQUA + "[@] You have been called by " + ChatColor.GREEN + player.getDisplayName() + ChatColor.AQUA + " (All players).");
                    players.playSound(players.getLocation(),
                            Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                }
                msg = msg.replaceAll("@all", ChatColor.AQUA + "@all" + ChatColor.RESET);
            } else {
                for (Player players : onlinePlayers) {
                    if (msg.toLowerCase().contains("@ " + players.getName().toLowerCase()) || msg.toLowerCase().contains("@" + players.getName().toLowerCase())) {
                        msg = msg.replaceAll("(?i)@ " + players.getName() + " ", ChatColor.AQUA + "@ " + players.getName() + ChatColor.RESET + " ");
                        msg = msg.replaceAll("(?i)@ " + players.getName(), ChatColor.AQUA + "@ " + players.getName() + ChatColor.RESET + " ");
                        msg = msg.replaceAll("(?i)@" + players.getName(), ChatColor.AQUA + "@ " + players.getName() + ChatColor.RESET + " ");
                        players.sendMessage(ChatColor.AQUA + "[@] You have been called by " + ChatColor.GREEN + player.getDisplayName() + ChatColor.AQUA + ".");
                        players.playSound(players.getLocation(),
                                Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    }
                }
            }
            event.setMessage(msg);
        }
    }

    private void expireRequest(UUID key) {
        if (playerStatus.containsKey(key)) {
            Player sender = Bukkit.getPlayer(playerStatus.get(key));
            if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(key))) {
                Objects.requireNonNull(Bukkit.getPlayer(key)).sendMessage(ChatColor.AQUA + "Teleport request expired.");
            }
            if (Bukkit.getOnlinePlayers().contains(sender)) {
                Objects.requireNonNull(sender).sendMessage(ChatColor.AQUA + "Teleport request expired.");
            }
            playerStatus.remove(key);
        }
    }

    private void killEntity() {
        getConsoleSender().sendMessage("/kill @e[type=minecraft:zombie]");
        getConsoleSender().sendMessage("/kill @e[type=minecraft:skeleton]");
        getConsoleSender().sendMessage("/kill @e[type=minecraft:creeper]");
        getConsoleSender().sendMessage("/kill @e[type=minecraft:spider]");
    }

    private UUID getOfflineUUID(String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
    }
}
