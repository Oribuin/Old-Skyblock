package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public final class Island {
    // Try not to write trash here

    private final Location center;
    private final boolean locked;
    private final String name;
    private final UUID owner;
    private final int islandRange;
    private final Location spawnPoint;
    private final World world;

    public Island(String name, Location location, UUID owner, int islandRange) {
        this.name = name;
        this.owner = owner;
        this.islandRange = islandRange;
        this.world = Bukkit.getWorld("islands_normal");
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

    public World getWorld() {
        return this.world;
    }

    public Location getCenter() {
        return this.center;
    }

    public Location getSpawnPoint() {
        return this.spawnPoint;
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
 * Jesus christ get yourself together
 *
 */