package xyz.oribuin.skyblock.managers.island;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.utilities.Color;

import java.util.UUID;

public class IslandManager {
    private final Skyblock plugin;

    public IslandManager(final Skyblock skyblock) {
        this.plugin = skyblock;
    }

    public Island getIsland() {
        Island island = new Island();
    }

    public void createIsland(String name, UUID uuid) {
        Island island = new Island(plugin, 150, 150, uuid);

        island.setOwner(island, uuid);
        island.setWorldBorder(island.getIsland().getWorldBorder());
        island.setName(Color.msg(name));
        island.getWorld().getBlockAt(island.getCenter()).setType(Material.BEDROCK);
    }
}
