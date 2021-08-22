package org.meowcat.essential.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.meowcat.essential.Main;

public class PlayerChangedWorldEvent implements Listener {
    @EventHandler
    public void playerChangedWorldEvent(org.bukkit.event.player.PlayerChangedWorldEvent event) {
        FileConfiguration config = Main.plugin.getConfig();
        if (config.getBoolean("configuration.player-list-world-enabled", false)) {
            Player player = event.getPlayer();
            if (player.isOnline()) {
                // TODO: custom world name
                player.setPlayerListName(String.format(config.getString("configuration.player-list-world-identification", "[%s]"), player.getWorld().getName()) + player.getName());
            }
        }
    }
}
