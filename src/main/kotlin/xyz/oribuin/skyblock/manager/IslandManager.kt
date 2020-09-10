package xyz.oribuin.skyblock.manager

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.WorldEditException
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.event.IslandCreateEvent
import xyz.oribuin.skyblock.event.IslandDeleteEvent
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.island.IslandMember
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class IslandManager(plugin: Skyblock) : Manager(plugin) {
    val players = mutableMapOf<UUID, Island>()


    override fun reload() {
        // Unused
    }

    override fun disable() {
        // Unused
    }

    fun createIsland(name: String, schematicName: String, owner: UUID, islandRange: Int): Island {
        val island = Island(islandCount + 1, name, getNextAvailableLocation(), owner, islandRange)
        createSchematic(island, schematicName)

        plugin.getManager(DataManager::class).createIslandData(island)

        val player = Bukkit.getPlayer(owner)
        player?.let { plugin.getManager(DataManager::class).createUser(it, island) }
        player?.teleport(island.spawnPoint)
        Bukkit.getPluginManager().callEvent(IslandCreateEvent(island))
        return island
    }

    fun deleteIsland(island: Island) {
        // Delete island from world (Could cause lag, who knows honestly)

        val cube = plugin.getManager(MathManager::class).getCuboid(island.center, island.islandRange, island.center.y.toInt(), island.islandRange)


        // Cancer code incoming
        var x = cube[0].x
        while (x < cube[1].x) {
            var y = cube[0].y
            while (y < cube[1].y) {
                var z = cube[0].z
                while (z < cube[1].z) {
                    val location = Location(island.center.world, x, y, z)
                    val block = island.center.world?.getBlockAt(location)
                    if (block?.type != Material.AIR) {
                        block?.type = Material.AIR
                    }
                    z++
                }
                y++
            }
            x++
        }

        // Delete island from database
        plugin.getManager(DataManager::class).deleteIslandData(island)
        Bukkit.getPluginManager().callEvent(IslandDeleteEvent(island))
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
        return Location(Bukkit.getWorld(ConfigManager.Setting.WORLD.string),
                islandCount * ConfigManager.Setting.SETTINGS_SIZE.double + 50.500,
                65.0,
                islandCount * ConfigManager.Setting.SETTINGS_SIZE.double + 50.500).clone()
    }

    var islandCount = 0
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

    fun isOnOwnIsland(player: Player): Boolean {
        val member = IslandMember(plugin, player.uniqueId)

        if (!member.hasIsland && !member.islandOwner)
            return false

        val island = member.getIsland() ?: return false

        return island.center.world?.getNearbyEntities(island.center, island.islandRange.toDouble(), 256.0, island.islandRange.toDouble())?.contains(player)!!
    }

    private val tablePrefix: String
        get() = plugin.description.name.toLowerCase() + "_"
}
