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
        // Create World
        this.createWorld(this.plugin.config.getString("world-name") ?: "skyblock_islands")

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
    private fun createWorld(name: String) {

        // Create world synchronously because fuck spigot
        this.plugin.server.scheduler.runTask(plugin, Runnable {

            if (Bukkit.getWorld(name) == null) {
                world = WorldCreator.name(name)
                    .type(WorldType.FLAT)
                    .environment(World.Environment.NORMAL)
                    .generateStructures(false)
                    .generator(VoidGenerator())
                    .createWorld()
            }

        })


    }

}