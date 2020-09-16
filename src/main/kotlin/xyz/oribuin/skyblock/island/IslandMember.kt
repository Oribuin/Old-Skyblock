package xyz.oribuin.skyblock.island

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.manager.ConfigManager
import xyz.oribuin.skyblock.manager.DataManager
import java.util.*

class IslandMember(private val plugin: Skyblock, private val uuid: UUID) {

    private val data = plugin.getManager(DataManager::class)

    var hasIsland: Boolean = false
        get() {
            data.connector?.connect { connection ->
                val query = "SELECT * FROM ${data.tablePrefix}members WHERE uuid = ?"
                connection.prepareStatement(query).use { statement ->
                    statement.setString(1, uuid.toString())
                    val result = statement.executeQuery()
                    if (result.next())
                        field = result.getBoolean(1)
                }
            }

            return field
        }

    var islandOwner: Boolean = false
        get() {
            data.connector?.connect { connection ->
                val query = "SELECT islandOwner FROM ${data.tablePrefix}members WHERE uuid = ?"
                connection.prepareStatement(query).use { statement ->
                    statement.setString(1, uuid.toString())
                    val result = statement.executeQuery()
                    if (result.next())
                        field = result.getBoolean(1)
                }
            }

            return field
        }

    fun getIsland(): Island? {
        var island: Island? = null

        if (!hasIsland && !islandOwner)
            return null

        data.connector?.connect { connection ->
            val query = "SELECT island_id FROM ${data.tablePrefix}members WHERE uuid = ?"
            connection.prepareStatement(query).use { statement ->
                statement.setString(1, player.uniqueId.toString())
                val result = statement.executeQuery()
                if (result.next())
                    island = data.getIslandFromId(result.getInt(1))

            }
        }

        return island
    }

    fun onIsland(island: Island): Boolean {
        val player = Bukkit.getPlayer(uuid) ?: return false

        if (player.world != Bukkit.getWorld(ConfigManager.Setting.WORLD.string))
            return false

        return (island.center.world ?: return false).getNearbyEntities(island.center, island.islandRange.toDouble() / 2, 800.0, island.islandRange.toDouble() / 2).contains(player)
    }

    val player: OfflinePlayer = Bukkit.getOfflinePlayer(uuid)
}
