package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Location;

import java.util.UUID;

public final class Island {
    private final Location center;
    private boolean locked;
    private String name;
    private UUID owner;
    private int islandRange;
    private Location spawnPoint;

    public Island(String name, Location location, UUID owner, int islandRange) {
        this.name = name;
        this.owner = owner;
        this.islandRange = islandRange;
        this.center = location;
        this.spawnPoint = center;
        this.locked = false;
    }

    public int getIslandRange() {
        return this.islandRange;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getName() {
        return this.name;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public Location getCenter() {
        return this.center;
    }

    public Location getSpawnPoint() {
        return this.spawnPoint;
    }
}
