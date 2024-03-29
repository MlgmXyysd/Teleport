package org.meowcat.essential;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.meowcat.essential.commands.*;
import org.meowcat.essential.commands.home.back;
import org.meowcat.essential.commands.home.home;
import org.meowcat.essential.commands.home.set;
import org.meowcat.essential.commands.home.spawnpoint;
import org.meowcat.essential.commands.skin.Change;
import org.meowcat.essential.commands.teleport.*;
import org.meowcat.essential.commands.whitelist.add;
import org.meowcat.essential.events.*;
import org.meowcat.essential.utils.LanguageUtil;
import org.meowcat.essential.utils.LocationUtil;
import org.meowcat.essential.utils.PlayerStatusUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getConsoleSender;

public final class Main extends JavaPlugin implements Listener {
    public static Main plugin;
    public static boolean isLatest = false;
    private static List<String> entityList;

    public static void killEntity() {
        for (String entity : entityList) {
            Bukkit.getServer().dispatchCommand(getConsoleSender(), "kill @e[type=" + entity + "]");
        }
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    @Override
    public void onEnable() {
        saveDefaultConfig();
        long clearDelay = getConfig().getLong("configuration.auto-clear-entity-delay", 60000);
        plugin = this;
        entityList = getConfig().getStringList("configuration.entity-clear-list");
        new LocationUtil();
        new LanguageUtil();

        getServer().getPluginManager().registerEvents(new SignPlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new EntityExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerChangedWorldEvent(), this);

        Objects.requireNonNull(getCommand("entityclear")).setExecutor(new clearentity());
        Objects.requireNonNull(getCommand("hat")).setExecutor(new hat());
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new tpa());
        Objects.requireNonNull(getCommand("tprandom")).setExecutor(new random());
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new accept());
        Objects.requireNonNull(getCommand("tpahere")).setExecutor(new here());
        Objects.requireNonNull(getCommand("tpcancel")).setExecutor(new cancel());
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new deny());
        Objects.requireNonNull(getCommand("toggle")).setExecutor(new toggle());
        Objects.requireNonNull(getCommand("wladd")).setExecutor(new add());
        Objects.requireNonNull(getCommand("home")).setExecutor(new home());
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new set());
        Objects.requireNonNull(getCommand("setspawnpoint")).setExecutor(new spawnpoint());
        Objects.requireNonNull(getCommand("back")).setExecutor(new back());
        Objects.requireNonNull(getCommand("perform")).setExecutor(new perform());
        Objects.requireNonNull(getCommand("skinchange")).setExecutor(new Change());
        Objects.requireNonNull(getCommand("tpall")).setExecutor(new all());
        Objects.requireNonNull(getCommand("suicide")).setExecutor(new suicide());

        Objects.requireNonNull(getCommand("tpa")).setTabCompleter(new PlayerStatusUtil());
        Objects.requireNonNull(getCommand("tpahere")).setTabCompleter(new PlayerStatusUtil());
        Objects.requireNonNull(getCommand("tpcancel")).setTabCompleter(new PlayerStatusUtil());
        Objects.requireNonNull(getCommand("wladd")).setTabCompleter(new PlayerStatusUtil());
        Objects.requireNonNull(getCommand("toggle")).setTabCompleter(new toggle());


        if (getConfig().getBoolean("configuration.rotten-to-leather", false)) {
            FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(plugin, NamespacedKey.MINECRAFT), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, (float) 0.35, 100);
            getServer().addRecipe(recipe);
        }

        getConsoleSender().sendMessage("[MeowEssential] Meow Essential by MeowCat Studio");
        getConsoleSender().sendMessage("[MeowEssential] Official Website http://www.meowcat.org/");
        getConsoleSender().sendMessage("[MeowEssential] Plugin enabled.");
        if (getConfig().getBoolean("configuration.auto-clear-entity", false)) {
            getServer().getScheduler().runTaskTimer(this, Main::killEntity, clearDelay, clearDelay);
        }
        if (getConfig().getBoolean("configuration.update-check", true)) {
            getConsoleSender().sendMessage("[MeowEssential] Checking plugin update...");
            new BukkitRunnable() {
                public void run() {
                    String latestVer = "";
                    try {
                        URL url = new URL("http://www.meowcat.org/minecraft/essential/version.mct");
                        InputStream is = url.openStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        latestVer = br.readLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String current = getDescription().getVersion();
                    if (latestVer.equalsIgnoreCase(current)) {
                        getConsoleSender().sendMessage("[MeowEssential] You are running Meow Essential latest version.");
                        isLatest = true;
                    } else {
                        getConsoleSender().sendMessage("[MeowEssential] Update found: " + latestVer);
                    }
                }
            }.runTaskAsynchronously(this);
        }
    }

    @Override
    public void onDisable() {
        //saveConfig();
        getConsoleSender().sendMessage("[MeowEssential] Meow Essential by MeowCat Studio");
        getConsoleSender().sendMessage("[MeowEssential] Official Website http://www.meowcat.org/");
        getConsoleSender().sendMessage("[MeowEssential] Plugin disabled.");
    }
}
