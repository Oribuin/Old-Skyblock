package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import xyz.oribuin.skyblock.events.api.OwnerChangeEvent;

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

    public void setIslandRange(int islandRange) {
        this.islandRange = islandRange;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID uuid) {
        OwnerChangeEvent ownerChangeEvent = new OwnerChangeEvent(owner, uuid);
        Bukkit.getPluginManager().callEvent(ownerChangeEvent);
        ownerChangeEvent.setOwner(uuid);
    }

    public Location getCenter() {
        return this.center;
    }

    public Location getSpawnPoint() {
        return this.spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}

/*
 * Here is just for me because I have gotten confused many times
 *
 * • You're defining the island methods
 * • So getRange() getMembers() ect
 * • Define size, players, upgrades, flags, everything.
 *
 * • This way you can use the methods across the plugin.
 *
 * Jesus christ get yourself together
 */