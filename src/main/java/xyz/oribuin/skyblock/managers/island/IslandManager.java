package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class IslandManager {

    public void createIsland(String name, Location location, UUID owner, int islandRange) {
        Island island = new Island(name, location, owner, islandRange);
        island.getWorld().getBlockAt(location).setType(Material.BEDROCK);
        island.getWorld().getWorldBorder().setCenter(island.getCenter());
        island.getWorld().getWorldBorder().setSize(islandRange);
    }
}
