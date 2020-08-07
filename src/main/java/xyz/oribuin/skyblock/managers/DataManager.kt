package xyz.oribuin.skyblock.managers

import org.bukkit.Bukkit
import xyz.oribuin.skyblock.Skyblock
import xyz.oribuin.skyblock.database.DatabaseConnector
import xyz.oribuin.skyblock.database.MySQLConnector
import xyz.oribuin.skyblock.database.SQLiteConnector
import xyz.oribuin.skyblock.island.Island
import xyz.oribuin.skyblock.utils.FileUtils.createFile
import java.sql.Connection

class DataManager(plugin: Skyblock) : Manager(plugin) {
    private var connector: DatabaseConnector? = null

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
                "CREATE TABLE IF NOT EXISTS ${tablePrefix}islands (owner_uuid TXT, name, TXT, locked BOOLEAN, center_x DOUBLE, center_y, center_z DOUBLE, range INT, spawn_x DOUBLE, spawn_y DOUBLE, spawn_z DOUBLE)"
        )

        async(Runnable {
            connector?.connect { connection ->
                for (string in queries) {
                    connection.prepareStatement(string).use { statement -> statement.executeUpdate() }
                }
            }
        })
    }

    fun createIslandData(island: Island) {
        async(Runnable {
            connector?.connect { connection: Connection ->
                val createIslandData = "INSERT INTO ${tablePrefix}islands (owner_uuid, name, locked, center_x, center_y, center_z, range, spawn_x, spawn_y, spawn_z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                connection.prepareStatement(createIslandData).use { statement ->
                    statement.setString(1, island.owner.toString())
                    statement.setString(2, island.name)
                    statement.setBoolean(3, island.isLocked)
                    statement.setDouble(4, island.center.x)
                    statement.setDouble(5, island.center.y)
                    statement.setDouble(6, island.center.z)
                    statement.setInt(7, island.islandRange)
                    statement.setDouble(8, island.spawnPoint.x)
                    statement.setDouble(9, island.spawnPoint.y)
                    statement.setDouble(10, island.spawnPoint.z)

                    statement.executeUpdate()
                }
            }
        })
    }

    private fun async(asyncCallback: Runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, asyncCallback)
    }

    private val tablePrefix: String
        get() = plugin.description.name.toLowerCase() + "_"
}