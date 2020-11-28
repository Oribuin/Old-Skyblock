package xyz.oribuin.skyblock.manager

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.generator.ChunkGenerator
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.VoidGenerator
import xyz.oribuin.skyblock.library.FileUtils
import xyz.oribuin.skyblock.library.Manager

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
                    .type(WorldType.NORMAL)
                    .environment(enviroment)
                    .generateStructures(false)
                    .generator(worldGenerator)
            worldCreator.createWorld()
        }
    }

    private val worldGenerator: ChunkGenerator
        get() = VoidGenerator()
}
