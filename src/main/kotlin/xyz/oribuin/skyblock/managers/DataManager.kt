package xyz.oribuin.skyblock.managers

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.database.DatabaseConnector
import xyz.oribuin.skyblock.database.MySQLConnector
import xyz.oribuin.skyblock.database.SQLiteConnector
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.utils.FileUtils.createFile
import java.sql.Connection
import java.util.*

class DataManager(plugin: Skyblock) : Manager(plugin) {
    var connector: DatabaseConnector? = null
    private var island: Island? = null

    override fun reload() {
        try {
            if (ConfigManager.Setting.SQL_ENABLED.boolean) {
                val hostname = ConfigManager.Setting.SQL_HOSTNAME.string
                val port = ConfigManager.Setting.SQL_PORT.int
                val database = ConfigManager.Setting.SQL_DATABASENAME.string
                val username = ConfigManager.Setting.SQL_USERNAME.string
                val password = ConfigManager.Setting.SQL_PASSWORD.string
                val useSSL = ConfigManager.Setting.SQL_USE_SSL.boolean

                this.connector = MySQLConnector(this.plugin, hostname, port, database, username, password, useSSL)
                this.plugin.logger.info("Now using MySQL for the plugin Database.")
            } else {
                createFile(plugin, "skyblock.db")

                this.connector = SQLiteConnector(this.plugin)
                this.plugin.logger.info("Now using SQLite for the Plugin Database.")
            }

            this.createTables()

        } catch (ex: Exception) {
            this.plugin.logger.severe("Fatal error connecting to Database, Plugin has disabled itself.")
            Bukkit.getPluginManager().disablePlugin(this.plugin)
            ex.printStackTrace()
        }

    }

    override fun disable() {
        // Unused
    }

    private fun createTables() {

        val queries = arrayOf(
                "CREATE TABLE IF NOT EXISTS ${tablePrefix}islands (island_id INT, owner_uuid TXT, name, TXT, locked BOOLEAN, center_x DOUBLE, center_y DOUBLE, center_z DOUBLE, range INT, spawn_x DOUBLE, spawn_y DOUBLE, spawn_z DOUBLE)",
                "CREATE TABLE IF NOT EXISTS ${tablePrefix}members (island_id INT, uuid TXT, islandOwner BOOLEAN, PRIMARY KEY(uuid))"
        )

        async {
            connector?.connect { connection ->
                for (string in queries) {
                    connection.prepareStatement(string).use { statement -> statement.executeUpdate() }
                }
            }
        }
    }

    fun createIslandData(island: Island) {
        async {
            connector?.connect { connection: Connection ->
                val createIslandData = "REPLACE INTO ${tablePrefix}islands (island_id, owner_uuid, name, locked, center_x, center_y, center_z, range, spawn_x, spawn_y, spawn_z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                connection.prepareStatement(createIslandData).use { statement ->
                    statement.setInt(1, island.id)
                    statement.setString(2, island.owner.toString())
                    statement.setString(3, island.name)
                    statement.setBoolean(4, island.isLocked)
                    statement.setDouble(5, island.center.x)
                    statement.setDouble(6, island.center.y)
                    statement.setDouble(7, island.center.z)
                    statement.setInt(8, island.islandRange)
                    statement.setDouble(9, island.spawnPoint.x)
                    statement.setDouble(10, island.spawnPoint.y)
                    statement.setDouble(11, island.spawnPoint.z)

                    statement.executeUpdate()
                }
            }
        }
    }

    fun deleteIslandData(island: Island) {
        async {
            connector?.connect { connection ->
                val clearIsland = "DELETE FROM ${tablePrefix}islands WHERE island_id = ?"
                connection.prepareStatement(clearIsland).use { statement ->
                    statement.setInt(1, island.id)
                    statement.executeUpdate()
                }

                val clearMember = "DELETE FROM ${tablePrefix}members WHERE island_id = ?"
                connection.prepareStatement(clearMember).use { statement ->
                    statement.setInt(1, island.id)
                    statement.executeUpdate()
                }
            }
        }
    }


    fun getIslandFromId(id: Int): Island? {
        connector?.connect { connection ->
            val query = "SELECT * FROM ${tablePrefix}islands WHERE island_id = ?"
            connection.prepareStatement(query).use { statement ->
                statement.setInt(1, id)
                val result = statement.executeQuery()

                if (result.next()) {
                    val location = Location(Bukkit.getWorld(ConfigManager.Setting.WORLD.string), result.getDouble("center_x"), result.getDouble("center_y"), result.getDouble("result_z"))

                    island = Island(result.getInt("island_id"), result.getString("name"), location, UUID.fromString(result.getString("owner")), result.getInt("range"))
                }

            }
        }

        return island
    }

    fun createUser(player: Player, island: Island) {
        async {
            connector?.connect { connection ->
                val createUser = "REPLACE INTO ${tablePrefix}members (uuid, islandOwner,island_id) VALUES (?, ?, ?)"
                connection.prepareStatement(createUser).use { statement ->
                    statement.setString(1, player.uniqueId.toString())
                    statement.setBoolean(2, false)
                    statement.setInt(3, island.id)

                    statement.executeUpdate()
                }
            }
        }
    }

    fun async(asyncCallback: Runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, asyncCallback)
    }

    val tablePrefix: String
        get() = plugin.description.name.toLowerCase() + "_"
}