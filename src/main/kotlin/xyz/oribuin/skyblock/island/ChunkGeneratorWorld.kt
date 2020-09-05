package xyz.oribuin.skyblock.island

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import java.util.*

class ChunkGeneratorWorld : ChunkGenerator() {
    private fun generateChunks(world: World): ChunkData {
        val result = createChunkData(world)
        if (world.environment == World.Environment.NORMAL)
            result.setRegion(0, 0, 0, 16, 1, 16, Material.AIR)
        return result
    }

    override fun generateChunkData(world: World, random: Random, chunkX: Int, chunkZ: Int, biomeGrid: BiomeGrid): ChunkData {
        val result = createChunkData(world)
        if (world.environment == World.Environment.NORMAL) {
            result.setRegion(0, 0, 0, 16, 1, 16, Material.AIR)
            setBiome(biomeGrid)
        }
        return generateChunks(world)
    }

    override fun getDefaultPopulators(world: World): List<BlockPopulator> {
        return emptyList()
    }

    override fun canSpawn(world: World, x: Int, z: Int): Boolean {
        return true
    }

    private fun setBiome(biomeGrid: BiomeGrid) {
        val biome = Biome.PLAINS
        for (x in 0..15)
            for (z in 0..15)
                for (y in 0..255)
                    biomeGrid.setBiome(x, y, z, biome)
    }
}
