package xyz.oribuin.skyblock.generators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import xyz.oribuin.skyblock.Skyblock;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorWorld extends ChunkGenerator {
    public ChunkGeneratorWorld() {
        super();
    }

    public ChunkData generateChunks(World world) {
        ChunkData result = createChunkData(world);

        if (world.getEnvironment().equals(World.Environment.NORMAL))
            result.setRegion(0, 0, 0, 16, 1, 16, Material.WATER);

        return result;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        ChunkData result = createChunkData(world);

        if (world.getEnvironment().equals(World.Environment.NORMAL)) {
            result.setRegion(0, 0, 0, 16, 1, 16, Material.WATER);
            setBiome(biomeGrid);
        }

        return generateChunks(world);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(final World world) {
        return Collections.emptyList();
    }


    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    private void setBiome(BiomeGrid biomeGrid) {
        Biome biome = Biome.PLAINS;
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                for (int y = 0; y < 256; y++)
                    biomeGrid.setBiome(x, y, z, biome);
    }
}