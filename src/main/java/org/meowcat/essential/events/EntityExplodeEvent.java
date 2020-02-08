package org.meowcat.essential.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.meowcat.essential.Main;

public class EntityExplodeEvent implements Listener {
    @EventHandler
    private void entityExplodeEvent(org.bukkit.event.entity.EntityExplodeEvent e) {
        FileConfiguration config = Main.plugin.getConfig();
        Entity entity = e.getEntity();
        String type = entity.getType().name().toUpperCase();
        if (config.getBoolean("configuration.explode-block." + type + ".enabled", false) && config.getStringList("configuration.explode-block." + type + ".world").contains(entity.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
