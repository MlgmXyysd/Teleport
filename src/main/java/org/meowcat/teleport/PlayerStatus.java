package org.meowcat.teleport;

import lombok.Data;
import org.bukkit.entity.Player;

@Data
class PlayerStatus {
    private Player tpSender;
    private Player tpReceiver;
    private long expiredTime;
    private boolean isSend;

    PlayerStatus(Player playerA, Player playerB, long expireTime, boolean isSender) {
        tpSender = playerA;
        tpReceiver = playerB;
        expiredTime = expireTime;
        isSend = isSender;
    }
}
