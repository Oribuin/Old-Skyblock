package xyz.oribuin.skyblock.events.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class OwnerChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private UUID owner;
    private UUID newOwner;

    public OwnerChangeEvent(UUID owner, UUID newOwner) {
        this.owner = owner;
        this.newOwner = newOwner;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public UUID getOwner() {
        return owner;
    }

    public UUID getNewOwner() {
        return newOwner;
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
        this.newOwner = uuid;
    }
}
