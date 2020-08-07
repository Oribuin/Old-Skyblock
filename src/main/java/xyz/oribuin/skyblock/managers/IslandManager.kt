package xyz.oribuin.skyblock.managers

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.WorldEditException
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import org.bukkit.Bukkit
import org.bukkit.Location
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.island.Island
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class IslandManager(plugin: Skyblock) : Manager(plugin) {
    override fun reload() {
        // Unused
    }

    override fun disable() {
        // Unused
    }

    fun createIsland(name: String, schematicName: String, location: Location, owner: UUID, islandRange: Int): Island {
        val island = Island(name, location, owner, islandRange)
        this.createSchematic(island, schematicName)
        plugin.getManager(DataManager::class.java).createIslandData(island)

        return island
    }

    private fun createSchematic(island: Island, schematicName: String) {
        val world = island.center.world
        val file = File(plugin.dataFolder, "/schematics/$schematicName.schematic")
        if (world == null) return
        require(file.exists()) { "Invalid Schematic: $schematicName" }
        val clipboardFormat = ClipboardFormats.findByFile(file) ?: return
        try {
            clipboardFormat.getReader(FileInputStream(file)).use { reader ->
                val clipboard = reader.read()
                WorldEdit.getInstance().editSessionFactory.getEditSession(BukkitAdapter.adapt(world), -1).use { editSession ->
                    val operation = ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(island.center.x, island.center.y, island.center.z))
                            .build()
                    Operations.complete(operation)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: WorldEditException) {
            e.printStackTrace()
        }
    }

    fun getNextAvailableLocation(): Location {
        return Location(Bukkit.getWorld("Islands"), 0.0, 65.0, 0.0)
    }
}