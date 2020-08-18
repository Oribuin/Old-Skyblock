package xyz.oribuin.skyblock.managers

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.ChunkGenerator
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.ChunkGeneratorWorld
import xyz.oribuin.skyblock.utils.FileUtils

class WorldManager(plugin: Skyblock) : Manager(plugin) {
    override fun reload() {
        ConfigManager.Setting.WORLD.string.let { createWorld(it, World.Environment.NORMAL) }
        FileUtils.createFolder(plugin, "schematics")
    }

    override fun disable() {
        // Unused
    }

    private fun createWorld(worldName: String, enviroment: World.Environment) {
        if (Bukkit.getWorld(worldName) == null) {
            val worldCreator = WorldCreator.name(worldName)
                    .type(WorldType.FLAT)
                    .environment(enviroment)
                    .generateStructures(false)
                    .generator(worldGenerator)
            worldCreator.createWorld()
        }
    }

    private val worldGenerator: ChunkGenerator
        get() = ChunkGeneratorWorld()
}