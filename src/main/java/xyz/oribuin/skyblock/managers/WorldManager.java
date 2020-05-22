package xyz.oribuin.skyblock.managers;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import xyz.oribuin.skyblock.Skyblock;
import xyz.oribuin.skyblock.managers.island.ChunkGeneratorWorld;

public class WorldManager extends Manager {

    public WorldManager(Skyblock plugin) {
        super(plugin);
    }

    @Override
    public void reload() {
        // Unused
    }


    public org.bukkit.World getWorld(String worldName) {
        return Bukkit.getWorld(worldName);
    }

    public void createWorld(String worldName) {
        WorldCreator worldCreator = WorldCreator.name(worldName).type(WorldType.FLAT).environment(org.bukkit.World.Environment.NORMAL).generateStructures(false).generator(this.getWorldGenerator());
        worldCreator.createWorld();
    }

    public ChunkGenerator getWorldGenerator() {
        return new ChunkGeneratorWorld();
    }
}
