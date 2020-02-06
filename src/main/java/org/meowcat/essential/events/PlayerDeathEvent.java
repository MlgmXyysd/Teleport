package org.meowcat.essential.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.meowcat.essential.utils.LocationUtil;
import org.meowcat.essential.utils.PermissionUtil;

public class PlayerDeathEvent implements Listener {
    @EventHandler
    public void playerDeathEvent(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (PermissionUtil.hasPermission(player, PermissionUtil.HOME_BACK)) {
            LocationUtil.setHome(player, "back");
        }
    }
}
