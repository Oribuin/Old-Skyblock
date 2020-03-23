package xyz.oribuin.skyblock.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class IslandTeleportEvent extends PlayerEvent implements Cancellable {

    public IslandTeleportEvent(Player player) {
        super(player);
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
