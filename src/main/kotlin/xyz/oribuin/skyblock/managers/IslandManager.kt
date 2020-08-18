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
import xyz.oribuin.skyblock.utils.FileUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class IslandManager(plugin: Skyblock) : Manager(plugin) {
    override fun reload() {
        FileUtils.createFolder(plugin, "schematics")
        FileUtils.createFile(plugin, File("${plugin.dataFolder}{${File.separator}schematics", "plains.schematic"))
    }

    override fun disable() {
        // Unused
    }

    fun createIsland(name: String, schematicName: String, owner: UUID, islandRange: Int): Island {
        val island = Island(name, getNextAvailableLocation(), owner, islandRange)
        island.center = getNextAvailableLocation()
        this.createSchematic(island, schematicName)
        plugin.getManager(DataManager::class).createIslandData(island)

        return island
    }

    private fun createSchematic(island: Island, schematicName: String) {
        val world = island.center.world
        val file = File(plugin.dataFolder, "/schematics/$schematicName.schematic")
        if (world == null) return
        require(file.exists()) { "Attempted to generate invalid schematic $schematicName" }
        val clipboardFormat = ClipboardFormats.findByFile(file) ?: return
        try {
            clipboardFormat.getReader(FileInputStream(file)).use { reader ->
                val clipboard = reader.read()
                WorldEdit.getInstance().editSessionFactory.getEditSession(BukkitAdapter.adapt(world), -1).use { editSession ->
                    val operation = ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(getNextAvailableLocation().x, getNextAvailableLocation().y, getNextAvailableLocation().z))
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

    private fun getNextAvailableLocation(): Location {
        return Location(Bukkit.getWorld(ConfigManager.Setting.WORLD.string), islandCount * 200.0, 65.0, islandCount * -200.0).clone()
    }

    private var islandCount = 0
        get() {
            val dataManager = plugin.getManager(DataManager::class)
            dataManager.connector?.connect { connection ->
                connection.prepareStatement("SELECT COUNT(*) FROM ${tablePrefix}islands").use { statement ->
                    val result = statement.executeQuery();
                    result.next()
                    field = result.getInt(1)
                }
            }

            return field;
        }

    private val tablePrefix: String
        get() = plugin.description.name.toLowerCase() + "_"
}