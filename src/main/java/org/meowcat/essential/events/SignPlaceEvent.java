package org.meowcat.essential.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.meowcat.essential.utils.ColorUtil;
import org.meowcat.essential.utils.PermissionUtil;

import java.util.Objects;

public class SignPlaceEvent implements Listener {
    @EventHandler
    public void onChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (PermissionUtil.hasPermission(player, PermissionUtil.COLOR_SIGN)) {
            event.setLine(0, ColorUtil.replaceColor(Objects.requireNonNull(event.getLine(0))));
            event.setLine(1, ColorUtil.replaceColor(Objects.requireNonNull(event.getLine(1))));
            event.setLine(2, ColorUtil.replaceColor(Objects.requireNonNull(event.getLine(2))));
            event.setLine(3, ColorUtil.replaceColor(Objects.requireNonNull(event.getLine(3))));
        }
    }
}
