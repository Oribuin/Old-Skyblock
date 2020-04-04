package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Material;
import org.bukkit.WorldBorder;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.utilities.Color;

import java.util.UUID;

public class IslandManager {
    private final Skyblock plugin;

    public IslandManager(final Skyblock skyblock) {
        this.plugin = skyblock;
    }

    public void createIsland(String name, UUID uuid) {
        Island island = new Island(plugin, 150, 150, uuid, name);

        island.setOwner(island, uuid);
        island.getWorld().getBlockAt(island.getCenter()).setType(Material.BEDROCK);
    }
}
