package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public final class Island {
    // Don't write trash here.

    private Location center;
    private boolean locked;
    private String name;
    private UUID owner;
    private int islandRange;
    private World world;

    public Island(Location location, UUID owner, int islandRange) {
        setOwner(owner);
        world = location.getWorld();
        center = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.islandRange = islandRange;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
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
 */