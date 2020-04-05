package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.UUID;

public class IslandManager {

    public void createIsland(String name, Location location, UUID owner, int islandRange) {
        Island island = new Island(name, location, owner, islandRange);
        World world = island.getCenter().getWorld();
        if (world == null) return;

        world.getBlockAt(location).setType(Material.BEDROCK);
        world.getWorldBorder().setCenter(island.getCenter());
        world.getWorldBorder().setSize(islandRange);
    }
}
