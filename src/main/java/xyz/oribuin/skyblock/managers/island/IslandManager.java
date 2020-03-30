package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Material;
import xyz.oribuin.skyblock.Skyblock;

import java.util.UUID;

public class IslandManager {

    private final Skyblock plugin;

    public IslandManager(final Skyblock skyblock) {
        this.plugin = skyblock;
    }

    public void createIsland(UUID uuid) {
        Island island = new Island(plugin, 150, 150, uuid);

        island.setOwner(island, uuid);
        island.getWorld().getBlockAt(island.getCenter()).setType(Material.BEDROCK);
    }
}
