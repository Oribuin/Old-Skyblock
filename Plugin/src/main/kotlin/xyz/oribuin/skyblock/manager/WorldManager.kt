package xyz.oribuin.skyblock.manager

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import xyz.oribuin.orilibrary.manager.Manager
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.world.VoidGenerator
import java.io.File

class WorldManager(private val plugin: Skyblock) : Manager(plugin) {

    var world: World? = null

    override fun enable() {
        // Create Schematics Folder
        val file = File(plugin.dataFolder, "schematics")
        if (!file.exists()) {
            file.mkdir()
        }

    }

    override fun disable() {

    }

    /**
     * Create an empty world using the VoidGenerator
     *
     * @param name The name of the world.
     */
    fun createWorld(name: String) {

        world = if (Bukkit.getWorld(name) == null) {
            WorldCreator.name(name)
                .type(WorldType.FLAT)
                .environment(World.Environment.NORMAL)
                .generateStructures(false)
                .generator(VoidGenerator())
                .createWorld()
        } else {
            Bukkit.getWorld(name)
        }

    }

}